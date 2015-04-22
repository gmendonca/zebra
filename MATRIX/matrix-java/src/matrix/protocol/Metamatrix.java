// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: metamatrix.proto

package matrix.protocol;

public final class Metamatrix {
  private Metamatrix() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface MatrixMsgOrBuilder extends
      // @@protoc_insertion_point(interface_extends:matrix.protocol.MatrixMsg)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required string msgType = 1;</code>
     */
    boolean hasMsgType();
    /**
     * <code>required string msgType = 1;</code>
     */
    java.lang.String getMsgType();
    /**
     * <code>required string msgType = 1;</code>
     */
    com.google.protobuf.ByteString
        getMsgTypeBytes();

    /**
     * <code>optional string extraInfo = 2;</code>
     */
    boolean hasExtraInfo();
    /**
     * <code>optional string extraInfo = 2;</code>
     */
    java.lang.String getExtraInfo();
    /**
     * <code>optional string extraInfo = 2;</code>
     */
    com.google.protobuf.ByteString
        getExtraInfoBytes();

    /**
     * <code>optional int64 count = 3;</code>
     */
    boolean hasCount();
    /**
     * <code>optional int64 count = 3;</code>
     */
    long getCount();

    /**
     * <code>repeated string tasks = 4;</code>
     */
    com.google.protobuf.ProtocolStringList
        getTasksList();
    /**
     * <code>repeated string tasks = 4;</code>
     */
    int getTasksCount();
    /**
     * <code>repeated string tasks = 4;</code>
     */
    java.lang.String getTasks(int index);
    /**
     * <code>repeated string tasks = 4;</code>
     */
    com.google.protobuf.ByteString
        getTasksBytes(int index);
  }
  /**
   * Protobuf type {@code matrix.protocol.MatrixMsg}
   */
  public static final class MatrixMsg extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:matrix.protocol.MatrixMsg)
      MatrixMsgOrBuilder {
    // Use MatrixMsg.newBuilder() to construct.
    private MatrixMsg(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private MatrixMsg(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final MatrixMsg defaultInstance;
    public static MatrixMsg getDefaultInstance() {
      return defaultInstance;
    }

    public MatrixMsg getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private MatrixMsg(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000001;
              msgType_ = bs;
              break;
            }
            case 18: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000002;
              extraInfo_ = bs;
              break;
            }
            case 24: {
              bitField0_ |= 0x00000004;
              count_ = input.readInt64();
              break;
            }
            case 34: {
              com.google.protobuf.ByteString bs = input.readBytes();
              if (!((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
                tasks_ = new com.google.protobuf.LazyStringArrayList();
                mutable_bitField0_ |= 0x00000008;
              }
              tasks_.add(bs);
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
          tasks_ = tasks_.getUnmodifiableView();
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return matrix.protocol.Metamatrix.internal_static_matrix_protocol_MatrixMsg_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return matrix.protocol.Metamatrix.internal_static_matrix_protocol_MatrixMsg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              matrix.protocol.Metamatrix.MatrixMsg.class, matrix.protocol.Metamatrix.MatrixMsg.Builder.class);
    }

    public static com.google.protobuf.Parser<MatrixMsg> PARSER =
        new com.google.protobuf.AbstractParser<MatrixMsg>() {
      public MatrixMsg parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new MatrixMsg(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<MatrixMsg> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int MSGTYPE_FIELD_NUMBER = 1;
    private java.lang.Object msgType_;
    /**
     * <code>required string msgType = 1;</code>
     */
    public boolean hasMsgType() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required string msgType = 1;</code>
     */
    public java.lang.String getMsgType() {
      java.lang.Object ref = msgType_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          msgType_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string msgType = 1;</code>
     */
    public com.google.protobuf.ByteString
        getMsgTypeBytes() {
      java.lang.Object ref = msgType_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        msgType_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int EXTRAINFO_FIELD_NUMBER = 2;
    private java.lang.Object extraInfo_;
    /**
     * <code>optional string extraInfo = 2;</code>
     */
    public boolean hasExtraInfo() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional string extraInfo = 2;</code>
     */
    public java.lang.String getExtraInfo() {
      java.lang.Object ref = extraInfo_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          extraInfo_ = s;
        }
        return s;
      }
    }
    /**
     * <code>optional string extraInfo = 2;</code>
     */
    public com.google.protobuf.ByteString
        getExtraInfoBytes() {
      java.lang.Object ref = extraInfo_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        extraInfo_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int COUNT_FIELD_NUMBER = 3;
    private long count_;
    /**
     * <code>optional int64 count = 3;</code>
     */
    public boolean hasCount() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>optional int64 count = 3;</code>
     */
    public long getCount() {
      return count_;
    }

    public static final int TASKS_FIELD_NUMBER = 4;
    private com.google.protobuf.LazyStringList tasks_;
    /**
     * <code>repeated string tasks = 4;</code>
     */
    public com.google.protobuf.ProtocolStringList
        getTasksList() {
      return tasks_;
    }
    /**
     * <code>repeated string tasks = 4;</code>
     */
    public int getTasksCount() {
      return tasks_.size();
    }
    /**
     * <code>repeated string tasks = 4;</code>
     */
    public java.lang.String getTasks(int index) {
      return tasks_.get(index);
    }
    /**
     * <code>repeated string tasks = 4;</code>
     */
    public com.google.protobuf.ByteString
        getTasksBytes(int index) {
      return tasks_.getByteString(index);
    }

    private void initFields() {
      msgType_ = "";
      extraInfo_ = "";
      count_ = 0L;
      tasks_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasMsgType()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getMsgTypeBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getExtraInfoBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeInt64(3, count_);
      }
      for (int i = 0; i < tasks_.size(); i++) {
        output.writeBytes(4, tasks_.getByteString(i));
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getMsgTypeBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getExtraInfoBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(3, count_);
      }
      {
        int dataSize = 0;
        for (int i = 0; i < tasks_.size(); i++) {
          dataSize += com.google.protobuf.CodedOutputStream
            .computeBytesSizeNoTag(tasks_.getByteString(i));
        }
        size += dataSize;
        size += 1 * getTasksList().size();
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static matrix.protocol.Metamatrix.MatrixMsg parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static matrix.protocol.Metamatrix.MatrixMsg parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static matrix.protocol.Metamatrix.MatrixMsg parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static matrix.protocol.Metamatrix.MatrixMsg parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static matrix.protocol.Metamatrix.MatrixMsg parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static matrix.protocol.Metamatrix.MatrixMsg parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static matrix.protocol.Metamatrix.MatrixMsg parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static matrix.protocol.Metamatrix.MatrixMsg parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static matrix.protocol.Metamatrix.MatrixMsg parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static matrix.protocol.Metamatrix.MatrixMsg parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(matrix.protocol.Metamatrix.MatrixMsg prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code matrix.protocol.MatrixMsg}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:matrix.protocol.MatrixMsg)
        matrix.protocol.Metamatrix.MatrixMsgOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return matrix.protocol.Metamatrix.internal_static_matrix_protocol_MatrixMsg_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return matrix.protocol.Metamatrix.internal_static_matrix_protocol_MatrixMsg_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                matrix.protocol.Metamatrix.MatrixMsg.class, matrix.protocol.Metamatrix.MatrixMsg.Builder.class);
      }

      // Construct using matrix.protocol.Metamatrix.MatrixMsg.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        msgType_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        extraInfo_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        count_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000004);
        tasks_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000008);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return matrix.protocol.Metamatrix.internal_static_matrix_protocol_MatrixMsg_descriptor;
      }

      public matrix.protocol.Metamatrix.MatrixMsg getDefaultInstanceForType() {
        return matrix.protocol.Metamatrix.MatrixMsg.getDefaultInstance();
      }

      public matrix.protocol.Metamatrix.MatrixMsg build() {
        matrix.protocol.Metamatrix.MatrixMsg result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public matrix.protocol.Metamatrix.MatrixMsg buildPartial() {
        matrix.protocol.Metamatrix.MatrixMsg result = new matrix.protocol.Metamatrix.MatrixMsg(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.msgType_ = msgType_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.extraInfo_ = extraInfo_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.count_ = count_;
        if (((bitField0_ & 0x00000008) == 0x00000008)) {
          tasks_ = tasks_.getUnmodifiableView();
          bitField0_ = (bitField0_ & ~0x00000008);
        }
        result.tasks_ = tasks_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof matrix.protocol.Metamatrix.MatrixMsg) {
          return mergeFrom((matrix.protocol.Metamatrix.MatrixMsg)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(matrix.protocol.Metamatrix.MatrixMsg other) {
        if (other == matrix.protocol.Metamatrix.MatrixMsg.getDefaultInstance()) return this;
        if (other.hasMsgType()) {
          bitField0_ |= 0x00000001;
          msgType_ = other.msgType_;
          onChanged();
        }
        if (other.hasExtraInfo()) {
          bitField0_ |= 0x00000002;
          extraInfo_ = other.extraInfo_;
          onChanged();
        }
        if (other.hasCount()) {
          setCount(other.getCount());
        }
        if (!other.tasks_.isEmpty()) {
          if (tasks_.isEmpty()) {
            tasks_ = other.tasks_;
            bitField0_ = (bitField0_ & ~0x00000008);
          } else {
            ensureTasksIsMutable();
            tasks_.addAll(other.tasks_);
          }
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasMsgType()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        matrix.protocol.Metamatrix.MatrixMsg parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (matrix.protocol.Metamatrix.MatrixMsg) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object msgType_ = "";
      /**
       * <code>required string msgType = 1;</code>
       */
      public boolean hasMsgType() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required string msgType = 1;</code>
       */
      public java.lang.String getMsgType() {
        java.lang.Object ref = msgType_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            msgType_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string msgType = 1;</code>
       */
      public com.google.protobuf.ByteString
          getMsgTypeBytes() {
        java.lang.Object ref = msgType_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          msgType_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string msgType = 1;</code>
       */
      public Builder setMsgType(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        msgType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string msgType = 1;</code>
       */
      public Builder clearMsgType() {
        bitField0_ = (bitField0_ & ~0x00000001);
        msgType_ = getDefaultInstance().getMsgType();
        onChanged();
        return this;
      }
      /**
       * <code>required string msgType = 1;</code>
       */
      public Builder setMsgTypeBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        msgType_ = value;
        onChanged();
        return this;
      }

      private java.lang.Object extraInfo_ = "";
      /**
       * <code>optional string extraInfo = 2;</code>
       */
      public boolean hasExtraInfo() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional string extraInfo = 2;</code>
       */
      public java.lang.String getExtraInfo() {
        java.lang.Object ref = extraInfo_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            extraInfo_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string extraInfo = 2;</code>
       */
      public com.google.protobuf.ByteString
          getExtraInfoBytes() {
        java.lang.Object ref = extraInfo_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          extraInfo_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string extraInfo = 2;</code>
       */
      public Builder setExtraInfo(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        extraInfo_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string extraInfo = 2;</code>
       */
      public Builder clearExtraInfo() {
        bitField0_ = (bitField0_ & ~0x00000002);
        extraInfo_ = getDefaultInstance().getExtraInfo();
        onChanged();
        return this;
      }
      /**
       * <code>optional string extraInfo = 2;</code>
       */
      public Builder setExtraInfoBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        extraInfo_ = value;
        onChanged();
        return this;
      }

      private long count_ ;
      /**
       * <code>optional int64 count = 3;</code>
       */
      public boolean hasCount() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>optional int64 count = 3;</code>
       */
      public long getCount() {
        return count_;
      }
      /**
       * <code>optional int64 count = 3;</code>
       */
      public Builder setCount(long value) {
        bitField0_ |= 0x00000004;
        count_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int64 count = 3;</code>
       */
      public Builder clearCount() {
        bitField0_ = (bitField0_ & ~0x00000004);
        count_ = 0L;
        onChanged();
        return this;
      }

      private com.google.protobuf.LazyStringList tasks_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      private void ensureTasksIsMutable() {
        if (!((bitField0_ & 0x00000008) == 0x00000008)) {
          tasks_ = new com.google.protobuf.LazyStringArrayList(tasks_);
          bitField0_ |= 0x00000008;
         }
      }
      /**
       * <code>repeated string tasks = 4;</code>
       */
      public com.google.protobuf.ProtocolStringList
          getTasksList() {
        return tasks_.getUnmodifiableView();
      }
      /**
       * <code>repeated string tasks = 4;</code>
       */
      public int getTasksCount() {
        return tasks_.size();
      }
      /**
       * <code>repeated string tasks = 4;</code>
       */
      public java.lang.String getTasks(int index) {
        return tasks_.get(index);
      }
      /**
       * <code>repeated string tasks = 4;</code>
       */
      public com.google.protobuf.ByteString
          getTasksBytes(int index) {
        return tasks_.getByteString(index);
      }
      /**
       * <code>repeated string tasks = 4;</code>
       */
      public Builder setTasks(
          int index, java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureTasksIsMutable();
        tasks_.set(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string tasks = 4;</code>
       */
      public Builder addTasks(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureTasksIsMutable();
        tasks_.add(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string tasks = 4;</code>
       */
      public Builder addAllTasks(
          java.lang.Iterable<java.lang.String> values) {
        ensureTasksIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, tasks_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string tasks = 4;</code>
       */
      public Builder clearTasks() {
        tasks_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000008);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string tasks = 4;</code>
       */
      public Builder addTasksBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureTasksIsMutable();
        tasks_.add(value);
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:matrix.protocol.MatrixMsg)
    }

    static {
      defaultInstance = new MatrixMsg(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:matrix.protocol.MatrixMsg)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_matrix_protocol_MatrixMsg_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_matrix_protocol_MatrixMsg_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020metamatrix.proto\022\017matrix.protocol\"M\n\tM" +
      "atrixMsg\022\017\n\007msgType\030\001 \002(\t\022\021\n\textraInfo\030\002" +
      " \001(\t\022\r\n\005count\030\003 \001(\003\022\r\n\005tasks\030\004 \003(\t"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_matrix_protocol_MatrixMsg_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_matrix_protocol_MatrixMsg_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_matrix_protocol_MatrixMsg_descriptor,
        new java.lang.String[] { "MsgType", "ExtraInfo", "Count", "Tasks", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
