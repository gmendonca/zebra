#!/bin/bash

LOG=Setup_$$.log

# Is this valid/required for AWS ?
check_project ()
{
    RESULT=$(gcutil listinstances --project=$GCE_PROJECTID 2>&1)
}

start_slave ()
{
    echo "Starting slave(s) $*"
    ./aws.py start_slave $*
}

stop_n_slaves()
{
    COUNT=1
    [[ ! -z "$1" ]] && COUNT=$1
    echo "Stopping $COUNT instances :"
    INSTANCES=$(list_resources | grep slave | awk '{print $1}' | tail -n $COUNT)
    echo "$INSTANCES"
    ./aws.py stop_node $INSTANCES
}


# Start N slaves in parallel ?
# This script ensures that only the specified number of slaves are active
start_n_slaves ()
{
    echo "In start_n_slaves"
    COUNT=$1
    CURRENT=1
    out=$(list_resources | grep "swift-slave")
    if [[ "$?" == 0 ]]
    then
        echo "Current slaves"
        echo "${out[*]}"
        CURRENT=$(list_resources | grep "swift-slave" | wc -l)
        echo "Count : " $CURRENT
        echo "New slaves needed : $(($COUNT - $CURRENT))"
    fi

    for i in $(seq $CURRENT 1 $COUNT)
    do
        start_slave swift-slave-$i &> $LOG &
    done
    wait
    list_resources
}

start_n_more ()
{
    ACTIVE=$(./aws.py list_resources | grep slave | wc -l)
    MORE=$1
    start_slave $(printf "swift-slave-%03d " $(seq $(($ACTIVE+1)) 1 $(($ACTIVE+$MORE)) ) )
    list_resources
}

stop_headnode()
{
    echo "Stopping headnode"
    ./aws.py stop_headnode
}

generate_swiftproperties()
{
    EXTERNAL_IP=$(gcutil --project=$GCE_PROJECTID listinstances | grep headnode | awk '{ print $10 }')
    SERVICE_PORT=50010
    echo http://$EXTERNAL_IP:$SERVICE_PORT > PUBLIC_ADDRESS
    cat <<EOF > swift.properties
site=cloud,local
use.provider.staging=true
execution.retries=2

site.local {
   jobmanager=local
   initialScore=10000
   filesystem=local
   workdir=/tmp/swiftwork
}

site.cloud {
   taskWalltime=04:00:00
   initialScore=10000
   filesystem=local
   jobmanager=coaster-persistent:local:local:http://$EXTERNAL_IP:$SERVICE_PORT
   slaveManager=passive
   taskThrottle=800
   workdir=/home/$USER/work
}

EOF
}

list_resources()
{
    ./aws.py list_resources
}

dissolve()
{
    ./aws.py dissolve
}

start_headnode()
{
    ./aws.py start_headnode
}

start_slaves()
{
    SLAVES_REQUESTED=$AWS_SLAVE_COUNT
    CURRENT_COUNT=$(list_resources | grep "swift-slave" | wc -l)
    echo "Current slaves   : $CURRENT_COUNT"
    echo "Workers requested : $SLAVES_REQUESTED"
    SLAVES_REQUIRED=$(($SLAVES_REQUESTED - $CURRENT_COUNT))
    if [[ $SLAVES_REQUIRED -gt 0 ]]
    then
        #printf("swift-slave-%03d", {$CURRENT_COUNT..$(($CURRENT_COUNT+$COUNT_NEEDED))})
        END=$(($CURRENT_COUNT+$SLAVES_REQUIRED-1))
        start_slave $(printf "swift-slave-%03d " $(seq $CURRENT_COUNT 1 $END))
    else
        echo "No additional slaves needed"
    fi
}

connect()
{
    source configs
    NODE=$1
    [[ -z $1 ]] && NODE="headnode"
    [[ -z $AWS_USERNAME ]] && AWS_USERNAME="ec2-user"

    IP=$(./aws.py list_resource $NODE)
    echo "Connecting to AWS node:$NODE on $IP as $AWS_USERNAME"
    ssh -A -o StrictHostKeyChecking=no -l $AWS_USERNAME -i $AWS_KEYPAIR_FILE $IP
}


init()
{
    source configs
    start_headnode
    start_slaves
    list_resources
}

init
