#!/usr/bin/env python

import os
import pprint

def _read_conf(config_file):
    cfile = open(config_file, 'r').read()
    config = {}
    for line in cfile.split('\n'):

        # Checking if empty line or comment
        if line.startswith('#') or not line :
            continue

        temp = line.split('=')
        config[temp[0]] = temp[1].strip('\r')
    return config

def pretty_configs(configs):
    printer = pprint.PrettyPrinter(indent=4)
    printer.pprint(configs)


def read_configs(config_file):
    config = _read_conf(config_file)

    if 'AWS_CREDENTIALS_FILE' in config :
        config['AWS_CREDENTIALS_FILE'] =  os.path.expanduser(config['AWS_CREDENTIALS_FILE'])
        config['AWS_CREDENTIALS_FILE'] =  os.path.expandvars(config['AWS_CREDENTIALS_FILE'])

        cred_lines    =  open(config['AWS_CREDENTIALS_FILE']).readlines()
        cred_details  =  cred_lines[1].split(',')
        credentials   = { 'AWS_Username'   : cred_details[0],
                          'AWSAccessKeyId' : cred_details[1],
                          'AWSSecretKey'   : cred_details[2] }
        config.update(credentials)
    else:
        print "AWS_CREDENTIALS_FILE , Missing"
        print "ERROR: Cannot proceed without access to AWS_CREDENTIALS_FILE"
        exit(-1)

    if 'AWS_KEYPAIR_FILE' in config:
        config['AWS_KEYPAIR_FILE'] = os.path.expanduser(config['AWS_KEYPAIR_FILE'])
        config['AWS_KEYPAIR_FILE'] = os.path.expandvars(config['AWS_KEYPAIR_FILE'])
    return config

#configs = read_configs("./configs")
#pretty_configs(configs)

#!/usr/bin/env python

HEADNODE_USERDATA_TRUNK='''#!/bin/bash
SLAVEPORT="50005"; SERVICEPORT="50010"

coaster_loop ()
{
    while :
    do
        coaster-service -p $SERVICEPORT -localport $SLAVEPORT -nosec -passive &> /var/log/coaster-service.logs
        sleep 10;
    done
}
coaster_loop &
'''


SLAVE_USERDATA_TRUNK='''#!/bin/bash
#Replace_me
HEADNODE=SET_HEADNODE_IP
CONCURRENCY="SET_CONCURRENCY"
SLAVEPORT="50005"

slave_loop ()
{
    while :
    do
        echo "Connecting to HEADNODE on $HEADNODE"
        slave.pl -w 3600 $CONCURRENCY http://$HEADNODE:$SLAVEPORT $HOSTNAME /var/log
        sleep 5
    done
}
slave_loop &
'''


def getstring(target):
    if target == "headnode":
        return HEADNODE_USERDATA_TRUNK
    elif target == "slave":
        return SLAVE_USERDATA_TRUNK
    else:
        return -1

