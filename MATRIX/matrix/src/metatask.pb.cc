// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: metatask.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "metatask.pb.h"

#include <algorithm>

#include <google/protobuf/stubs/common.h>
#include <google/protobuf/stubs/once.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/wire_format_lite_inl.h>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/generated_message_reflection.h>
#include <google/protobuf/reflection_ops.h>
#include <google/protobuf/wire_format.h>
// @@protoc_insertion_point(includes)

namespace {

const ::google::protobuf::Descriptor* TaskMsg_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  TaskMsg_reflection_ = NULL;

}  // namespace


void protobuf_AssignDesc_metatask_2eproto() {
  protobuf_AddDesc_metatask_2eproto();
  const ::google::protobuf::FileDescriptor* file =
    ::google::protobuf::DescriptorPool::generated_pool()->FindFileByName(
      "metatask.proto");
  GOOGLE_CHECK(file != NULL);
  TaskMsg_descriptor_ = file->message_type(0);
  static const int TaskMsg_offsets_[5] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TaskMsg, taskid_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TaskMsg, user_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TaskMsg, dir_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TaskMsg, cmd_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TaskMsg, datalength_),
  };
  TaskMsg_reflection_ =
    ::google::protobuf::internal::GeneratedMessageReflection::NewGeneratedMessageReflection(
      TaskMsg_descriptor_,
      TaskMsg::default_instance_,
      TaskMsg_offsets_,
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TaskMsg, _has_bits_[0]),
      -1,
      -1,
      sizeof(TaskMsg),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TaskMsg, _internal_metadata_),
      -1);
}

namespace {

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AssignDescriptors_once_);
inline void protobuf_AssignDescriptorsOnce() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AssignDescriptors_once_,
                 &protobuf_AssignDesc_metatask_2eproto);
}

void protobuf_RegisterTypes(const ::std::string&) {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
      TaskMsg_descriptor_, &TaskMsg::default_instance());
}

}  // namespace

void protobuf_ShutdownFile_metatask_2eproto() {
  delete TaskMsg::default_instance_;
  delete TaskMsg_reflection_;
}

void protobuf_AddDesc_metatask_2eproto() {
  static bool already_here = false;
  if (already_here) return;
  already_here = true;
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::google::protobuf::DescriptorPool::InternalAddGeneratedFile(
    "\n\016metatask.proto\"U\n\007TaskMsg\022\016\n\006taskId\030\001 "
    "\002(\t\022\014\n\004user\030\002 \002(\t\022\013\n\003dir\030\003 \002(\t\022\013\n\003cmd\030\004 "
    "\002(\t\022\022\n\ndataLength\030\005 \002(\003", 103);
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedFile(
    "metatask.proto", &protobuf_RegisterTypes);
  TaskMsg::default_instance_ = new TaskMsg();
  TaskMsg::default_instance_->InitAsDefaultInstance();
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_metatask_2eproto);
}

// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_metatask_2eproto {
  StaticDescriptorInitializer_metatask_2eproto() {
    protobuf_AddDesc_metatask_2eproto();
  }
} static_descriptor_initializer_metatask_2eproto_;

namespace {

static void MergeFromFail(int line) GOOGLE_ATTRIBUTE_COLD;
static void MergeFromFail(int line) {
  GOOGLE_CHECK(false) << __FILE__ << ":" << line;
}

}  // namespace


// ===================================================================

#ifndef _MSC_VER
const int TaskMsg::kTaskIdFieldNumber;
const int TaskMsg::kUserFieldNumber;
const int TaskMsg::kDirFieldNumber;
const int TaskMsg::kCmdFieldNumber;
const int TaskMsg::kDataLengthFieldNumber;
#endif  // !_MSC_VER

TaskMsg::TaskMsg()
  : ::google::protobuf::Message() , _internal_metadata_(NULL)  {
  SharedCtor();
  // @@protoc_insertion_point(constructor:TaskMsg)
}

void TaskMsg::InitAsDefaultInstance() {
}

TaskMsg::TaskMsg(const TaskMsg& from)
  : ::google::protobuf::Message(),
    _internal_metadata_(NULL) {
  SharedCtor();
  MergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:TaskMsg)
}

void TaskMsg::SharedCtor() {
  ::google::protobuf::internal::GetEmptyString();
  _cached_size_ = 0;
  taskid_.UnsafeSetDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  user_.UnsafeSetDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  dir_.UnsafeSetDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  cmd_.UnsafeSetDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  datalength_ = GOOGLE_LONGLONG(0);
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
}

TaskMsg::~TaskMsg() {
  // @@protoc_insertion_point(destructor:TaskMsg)
  SharedDtor();
}

void TaskMsg::SharedDtor() {
  taskid_.DestroyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  user_.DestroyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  dir_.DestroyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  cmd_.DestroyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  if (this != default_instance_) {
  }
}

void TaskMsg::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* TaskMsg::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return TaskMsg_descriptor_;
}

const TaskMsg& TaskMsg::default_instance() {
  if (default_instance_ == NULL) protobuf_AddDesc_metatask_2eproto();
  return *default_instance_;
}

TaskMsg* TaskMsg::default_instance_ = NULL;

TaskMsg* TaskMsg::New(::google::protobuf::Arena* arena) const {
  TaskMsg* n = new TaskMsg;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void TaskMsg::Clear() {
  if (_has_bits_[0 / 32] & 31) {
    if (has_taskid()) {
      taskid_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
    }
    if (has_user()) {
      user_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
    }
    if (has_dir()) {
      dir_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
    }
    if (has_cmd()) {
      cmd_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
    }
    datalength_ = GOOGLE_LONGLONG(0);
  }
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
  if (_internal_metadata_.have_unknown_fields()) {
    mutable_unknown_fields()->Clear();
  }
}

bool TaskMsg::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  // @@protoc_insertion_point(parse_start:TaskMsg)
  for (;;) {
    ::std::pair< ::google::protobuf::uint32, bool> p = input->ReadTagWithCutoff(127);
    tag = p.first;
    if (!p.second) goto handle_unusual;
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // required string taskId = 1;
      case 1: {
        if (tag == 10) {
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_taskid()));
          ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
            this->taskid().data(), this->taskid().length(),
            ::google::protobuf::internal::WireFormat::PARSE,
            "TaskMsg.taskId");
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(18)) goto parse_user;
        break;
      }

      // required string user = 2;
      case 2: {
        if (tag == 18) {
         parse_user:
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_user()));
          ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
            this->user().data(), this->user().length(),
            ::google::protobuf::internal::WireFormat::PARSE,
            "TaskMsg.user");
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(26)) goto parse_dir;
        break;
      }

      // required string dir = 3;
      case 3: {
        if (tag == 26) {
         parse_dir:
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_dir()));
          ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
            this->dir().data(), this->dir().length(),
            ::google::protobuf::internal::WireFormat::PARSE,
            "TaskMsg.dir");
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(34)) goto parse_cmd;
        break;
      }

      // required string cmd = 4;
      case 4: {
        if (tag == 34) {
         parse_cmd:
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_cmd()));
          ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
            this->cmd().data(), this->cmd().length(),
            ::google::protobuf::internal::WireFormat::PARSE,
            "TaskMsg.cmd");
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(40)) goto parse_dataLength;
        break;
      }

      // required int64 dataLength = 5;
      case 5: {
        if (tag == 40) {
         parse_dataLength:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &datalength_)));
          set_has_datalength();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectAtEnd()) goto success;
        break;
      }

      default: {
      handle_unusual:
        if (tag == 0 ||
            ::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
            ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
          goto success;
        }
        DO_(::google::protobuf::internal::WireFormat::SkipField(
              input, tag, mutable_unknown_fields()));
        break;
      }
    }
  }
success:
  // @@protoc_insertion_point(parse_success:TaskMsg)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:TaskMsg)
  return false;
#undef DO_
}

void TaskMsg::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:TaskMsg)
  // required string taskId = 1;
  if (has_taskid()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->taskid().data(), this->taskid().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "TaskMsg.taskId");
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      1, this->taskid(), output);
  }

  // required string user = 2;
  if (has_user()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->user().data(), this->user().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "TaskMsg.user");
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      2, this->user(), output);
  }

  // required string dir = 3;
  if (has_dir()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->dir().data(), this->dir().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "TaskMsg.dir");
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      3, this->dir(), output);
  }

  // required string cmd = 4;
  if (has_cmd()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->cmd().data(), this->cmd().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "TaskMsg.cmd");
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      4, this->cmd(), output);
  }

  // required int64 dataLength = 5;
  if (has_datalength()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(5, this->datalength(), output);
  }

  if (_internal_metadata_.have_unknown_fields()) {
    ::google::protobuf::internal::WireFormat::SerializeUnknownFields(
        unknown_fields(), output);
  }
  // @@protoc_insertion_point(serialize_end:TaskMsg)
}

::google::protobuf::uint8* TaskMsg::SerializeWithCachedSizesToArray(
    ::google::protobuf::uint8* target) const {
  // @@protoc_insertion_point(serialize_to_array_start:TaskMsg)
  // required string taskId = 1;
  if (has_taskid()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->taskid().data(), this->taskid().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "TaskMsg.taskId");
    target =
      ::google::protobuf::internal::WireFormatLite::WriteStringToArray(
        1, this->taskid(), target);
  }

  // required string user = 2;
  if (has_user()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->user().data(), this->user().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "TaskMsg.user");
    target =
      ::google::protobuf::internal::WireFormatLite::WriteStringToArray(
        2, this->user(), target);
  }

  // required string dir = 3;
  if (has_dir()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->dir().data(), this->dir().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "TaskMsg.dir");
    target =
      ::google::protobuf::internal::WireFormatLite::WriteStringToArray(
        3, this->dir(), target);
  }

  // required string cmd = 4;
  if (has_cmd()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->cmd().data(), this->cmd().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "TaskMsg.cmd");
    target =
      ::google::protobuf::internal::WireFormatLite::WriteStringToArray(
        4, this->cmd(), target);
  }

  // required int64 dataLength = 5;
  if (has_datalength()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt64ToArray(5, this->datalength(), target);
  }

  if (_internal_metadata_.have_unknown_fields()) {
    target = ::google::protobuf::internal::WireFormat::SerializeUnknownFieldsToArray(
        unknown_fields(), target);
  }
  // @@protoc_insertion_point(serialize_to_array_end:TaskMsg)
  return target;
}

int TaskMsg::RequiredFieldsByteSizeFallback() const {
  int total_size = 0;

  if (has_taskid()) {
    // required string taskId = 1;
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->taskid());
  }

  if (has_user()) {
    // required string user = 2;
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->user());
  }

  if (has_dir()) {
    // required string dir = 3;
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->dir());
  }

  if (has_cmd()) {
    // required string cmd = 4;
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->cmd());
  }

  if (has_datalength()) {
    // required int64 dataLength = 5;
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::Int64Size(
        this->datalength());
  }

  return total_size;
}
int TaskMsg::ByteSize() const {
  int total_size = 0;

  if (((_has_bits_[0] & 0x0000001f) ^ 0x0000001f) == 0) {  // All required fields are present.
    // required string taskId = 1;
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->taskid());

    // required string user = 2;
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->user());

    // required string dir = 3;
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->dir());

    // required string cmd = 4;
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->cmd());

    // required int64 dataLength = 5;
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::Int64Size(
        this->datalength());

  } else {
    total_size += RequiredFieldsByteSizeFallback();
  }
  if (_internal_metadata_.have_unknown_fields()) {
    total_size +=
      ::google::protobuf::internal::WireFormat::ComputeUnknownFieldsSize(
        unknown_fields());
  }
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = total_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void TaskMsg::MergeFrom(const ::google::protobuf::Message& from) {
  if (GOOGLE_PREDICT_FALSE(&from == this)) MergeFromFail(__LINE__);
  const TaskMsg* source =
    ::google::protobuf::internal::dynamic_cast_if_available<const TaskMsg*>(
      &from);
  if (source == NULL) {
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
    MergeFrom(*source);
  }
}

void TaskMsg::MergeFrom(const TaskMsg& from) {
  if (GOOGLE_PREDICT_FALSE(&from == this)) MergeFromFail(__LINE__);
  if (from._has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (from.has_taskid()) {
      set_has_taskid();
      taskid_.AssignWithDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), from.taskid_);
    }
    if (from.has_user()) {
      set_has_user();
      user_.AssignWithDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), from.user_);
    }
    if (from.has_dir()) {
      set_has_dir();
      dir_.AssignWithDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), from.dir_);
    }
    if (from.has_cmd()) {
      set_has_cmd();
      cmd_.AssignWithDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), from.cmd_);
    }
    if (from.has_datalength()) {
      set_datalength(from.datalength());
    }
  }
  if (from._internal_metadata_.have_unknown_fields()) {
    mutable_unknown_fields()->MergeFrom(from.unknown_fields());
  }
}

void TaskMsg::CopyFrom(const ::google::protobuf::Message& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void TaskMsg::CopyFrom(const TaskMsg& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool TaskMsg::IsInitialized() const {
  if ((_has_bits_[0] & 0x0000001f) != 0x0000001f) return false;

  return true;
}

void TaskMsg::Swap(TaskMsg* other) {
  if (other == this) return;
  InternalSwap(other);
}
void TaskMsg::InternalSwap(TaskMsg* other) {
  taskid_.Swap(&other->taskid_);
  user_.Swap(&other->user_);
  dir_.Swap(&other->dir_);
  cmd_.Swap(&other->cmd_);
  std::swap(datalength_, other->datalength_);
  std::swap(_has_bits_[0], other->_has_bits_[0]);
  _internal_metadata_.Swap(&other->_internal_metadata_);
  std::swap(_cached_size_, other->_cached_size_);
}

::google::protobuf::Metadata TaskMsg::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = TaskMsg_descriptor_;
  metadata.reflection = TaskMsg_reflection_;
  return metadata;
}


// @@protoc_insertion_point(namespace_scope)

// @@protoc_insertion_point(global_scope)