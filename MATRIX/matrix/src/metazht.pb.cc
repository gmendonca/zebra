// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: metazht.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include "metazht.pb.h"

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

const ::google::protobuf::Descriptor* Value_descriptor_ = NULL;
const ::google::protobuf::internal::GeneratedMessageReflection*
  Value_reflection_ = NULL;

}  // namespace


void protobuf_AssignDesc_metazht_2eproto() {
  protobuf_AddDesc_metazht_2eproto();
  const ::google::protobuf::FileDescriptor* file =
    ::google::protobuf::DescriptorPool::generated_pool()->FindFileByName(
      "metazht.proto");
  GOOGLE_CHECK(file != NULL);
  Value_descriptor_ = file->message_type(0);
  static const int Value_offsets_[17] = {
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, id_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, indegree_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, parents_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, children_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, datanamelist_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, datasize_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, alldatasize_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, tasklength_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, numtaskfin_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, numworksteal_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, numworkstealfail_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, numtaskwait_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, numtaskready_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, numcoreavilable_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, numallcore_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, outputsize_),
    GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, submittime_),
  };
  Value_reflection_ =
    ::google::protobuf::internal::GeneratedMessageReflection::NewGeneratedMessageReflection(
      Value_descriptor_,
      Value::default_instance_,
      Value_offsets_,
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, _has_bits_[0]),
      -1,
      -1,
      sizeof(Value),
      GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(Value, _internal_metadata_),
      -1);
}

namespace {

GOOGLE_PROTOBUF_DECLARE_ONCE(protobuf_AssignDescriptors_once_);
inline void protobuf_AssignDescriptorsOnce() {
  ::google::protobuf::GoogleOnceInit(&protobuf_AssignDescriptors_once_,
                 &protobuf_AssignDesc_metazht_2eproto);
}

void protobuf_RegisterTypes(const ::std::string&) {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedMessage(
      Value_descriptor_, &Value::default_instance());
}

}  // namespace

void protobuf_ShutdownFile_metazht_2eproto() {
  delete Value::default_instance_;
  delete Value_reflection_;
}

void protobuf_AddDesc_metazht_2eproto() {
  static bool already_here = false;
  if (already_here) return;
  already_here = true;
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::google::protobuf::DescriptorPool::InternalAddGeneratedFile(
    "\n\rmetazht.proto\"\335\002\n\005Value\022\n\n\002id\030\001 \002(\t\022\020\n"
    "\010indegree\030\002 \001(\003\022\017\n\007parents\030\003 \003(\t\022\020\n\010chil"
    "dren\030\004 \003(\t\022\024\n\014dataNameList\030\005 \003(\t\022\020\n\010data"
    "Size\030\006 \003(\003\022\023\n\013allDataSize\030\007 \001(\003\022\022\n\ntaskl"
    "ength\030\010 \001(\003\022\022\n\nnumTaskFin\030\t \001(\003\022\024\n\014numWo"
    "rkSteal\030\n \001(\003\022\030\n\020numWorkStealFail\030\013 \001(\003\022"
    "\023\n\013numTaskWait\030\014 \001(\005\022\024\n\014numTaskReady\030\r \001"
    "(\005\022\027\n\017numCoreAvilable\030\016 \001(\005\022\022\n\nnumAllCor"
    "e\030\017 \001(\005\022\022\n\noutputsize\030\020 \001(\003\022\022\n\nsubmitTim"
    "e\030\021 \001(\003", 367);
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedFile(
    "metazht.proto", &protobuf_RegisterTypes);
  Value::default_instance_ = new Value();
  Value::default_instance_->InitAsDefaultInstance();
  ::google::protobuf::internal::OnShutdown(&protobuf_ShutdownFile_metazht_2eproto);
}

// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer_metazht_2eproto {
  StaticDescriptorInitializer_metazht_2eproto() {
    protobuf_AddDesc_metazht_2eproto();
  }
} static_descriptor_initializer_metazht_2eproto_;

namespace {

static void MergeFromFail(int line) GOOGLE_ATTRIBUTE_COLD;
static void MergeFromFail(int line) {
  GOOGLE_CHECK(false) << __FILE__ << ":" << line;
}

}  // namespace


// ===================================================================

#ifndef _MSC_VER
const int Value::kIdFieldNumber;
const int Value::kIndegreeFieldNumber;
const int Value::kParentsFieldNumber;
const int Value::kChildrenFieldNumber;
const int Value::kDataNameListFieldNumber;
const int Value::kDataSizeFieldNumber;
const int Value::kAllDataSizeFieldNumber;
const int Value::kTasklengthFieldNumber;
const int Value::kNumTaskFinFieldNumber;
const int Value::kNumWorkStealFieldNumber;
const int Value::kNumWorkStealFailFieldNumber;
const int Value::kNumTaskWaitFieldNumber;
const int Value::kNumTaskReadyFieldNumber;
const int Value::kNumCoreAvilableFieldNumber;
const int Value::kNumAllCoreFieldNumber;
const int Value::kOutputsizeFieldNumber;
const int Value::kSubmitTimeFieldNumber;
#endif  // !_MSC_VER

Value::Value()
  : ::google::protobuf::Message() , _internal_metadata_(NULL)  {
  SharedCtor();
  // @@protoc_insertion_point(constructor:Value)
}

void Value::InitAsDefaultInstance() {
}

Value::Value(const Value& from)
  : ::google::protobuf::Message(),
    _internal_metadata_(NULL) {
  SharedCtor();
  MergeFrom(from);
  // @@protoc_insertion_point(copy_constructor:Value)
}

void Value::SharedCtor() {
  ::google::protobuf::internal::GetEmptyString();
  _cached_size_ = 0;
  id_.UnsafeSetDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  indegree_ = GOOGLE_LONGLONG(0);
  alldatasize_ = GOOGLE_LONGLONG(0);
  tasklength_ = GOOGLE_LONGLONG(0);
  numtaskfin_ = GOOGLE_LONGLONG(0);
  numworksteal_ = GOOGLE_LONGLONG(0);
  numworkstealfail_ = GOOGLE_LONGLONG(0);
  numtaskwait_ = 0;
  numtaskready_ = 0;
  numcoreavilable_ = 0;
  numallcore_ = 0;
  outputsize_ = GOOGLE_LONGLONG(0);
  submittime_ = GOOGLE_LONGLONG(0);
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
}

Value::~Value() {
  // @@protoc_insertion_point(destructor:Value)
  SharedDtor();
}

void Value::SharedDtor() {
  id_.DestroyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
  if (this != default_instance_) {
  }
}

void Value::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* Value::descriptor() {
  protobuf_AssignDescriptorsOnce();
  return Value_descriptor_;
}

const Value& Value::default_instance() {
  if (default_instance_ == NULL) protobuf_AddDesc_metazht_2eproto();
  return *default_instance_;
}

Value* Value::default_instance_ = NULL;

Value* Value::New(::google::protobuf::Arena* arena) const {
  Value* n = new Value;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void Value::Clear() {
#define OFFSET_OF_FIELD_(f) (reinterpret_cast<char*>(      \
  &reinterpret_cast<Value*>(16)->f) - \
   reinterpret_cast<char*>(16))

#define ZR_(first, last) do {                              \
    size_t f = OFFSET_OF_FIELD_(first);                    \
    size_t n = OFFSET_OF_FIELD_(last) - f + sizeof(last);  \
    ::memset(&first, 0, n);                                \
  } while (0)

  if (_has_bits_[0 / 32] & 195) {
    ZR_(alldatasize_, tasklength_);
    if (has_id()) {
      id_.ClearToEmptyNoArena(&::google::protobuf::internal::GetEmptyStringAlreadyInited());
    }
    indegree_ = GOOGLE_LONGLONG(0);
  }
  if (_has_bits_[8 / 32] & 65280) {
    ZR_(numtaskfin_, outputsize_);
  }
  submittime_ = GOOGLE_LONGLONG(0);

#undef OFFSET_OF_FIELD_
#undef ZR_

  parents_.Clear();
  children_.Clear();
  datanamelist_.Clear();
  datasize_.Clear();
  ::memset(_has_bits_, 0, sizeof(_has_bits_));
  if (_internal_metadata_.have_unknown_fields()) {
    mutable_unknown_fields()->Clear();
  }
}

bool Value::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  // @@protoc_insertion_point(parse_start:Value)
  for (;;) {
    ::std::pair< ::google::protobuf::uint32, bool> p = input->ReadTagWithCutoff(16383);
    tag = p.first;
    if (!p.second) goto handle_unusual;
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // required string id = 1;
      case 1: {
        if (tag == 10) {
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_id()));
          ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
            this->id().data(), this->id().length(),
            ::google::protobuf::internal::WireFormat::PARSE,
            "Value.id");
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(16)) goto parse_indegree;
        break;
      }

      // optional int64 indegree = 2;
      case 2: {
        if (tag == 16) {
         parse_indegree:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &indegree_)));
          set_has_indegree();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(26)) goto parse_parents;
        break;
      }

      // repeated string parents = 3;
      case 3: {
        if (tag == 26) {
         parse_parents:
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->add_parents()));
          ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
            this->parents(this->parents_size() - 1).data(),
            this->parents(this->parents_size() - 1).length(),
            ::google::protobuf::internal::WireFormat::PARSE,
            "Value.parents");
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(26)) goto parse_parents;
        if (input->ExpectTag(34)) goto parse_children;
        break;
      }

      // repeated string children = 4;
      case 4: {
        if (tag == 34) {
         parse_children:
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->add_children()));
          ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
            this->children(this->children_size() - 1).data(),
            this->children(this->children_size() - 1).length(),
            ::google::protobuf::internal::WireFormat::PARSE,
            "Value.children");
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(34)) goto parse_children;
        if (input->ExpectTag(42)) goto parse_dataNameList;
        break;
      }

      // repeated string dataNameList = 5;
      case 5: {
        if (tag == 42) {
         parse_dataNameList:
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->add_datanamelist()));
          ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
            this->datanamelist(this->datanamelist_size() - 1).data(),
            this->datanamelist(this->datanamelist_size() - 1).length(),
            ::google::protobuf::internal::WireFormat::PARSE,
            "Value.dataNameList");
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(42)) goto parse_dataNameList;
        if (input->ExpectTag(48)) goto parse_dataSize;
        break;
      }

      // repeated int64 dataSize = 6;
      case 6: {
        if (tag == 48) {
         parse_dataSize:
          DO_((::google::protobuf::internal::WireFormatLite::ReadRepeatedPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 1, 48, input, this->mutable_datasize())));
        } else if (tag == 50) {
          DO_((::google::protobuf::internal::WireFormatLite::ReadPackedPrimitiveNoInline<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, this->mutable_datasize())));
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(48)) goto parse_dataSize;
        if (input->ExpectTag(56)) goto parse_allDataSize;
        break;
      }

      // optional int64 allDataSize = 7;
      case 7: {
        if (tag == 56) {
         parse_allDataSize:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &alldatasize_)));
          set_has_alldatasize();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(64)) goto parse_tasklength;
        break;
      }

      // optional int64 tasklength = 8;
      case 8: {
        if (tag == 64) {
         parse_tasklength:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &tasklength_)));
          set_has_tasklength();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(72)) goto parse_numTaskFin;
        break;
      }

      // optional int64 numTaskFin = 9;
      case 9: {
        if (tag == 72) {
         parse_numTaskFin:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &numtaskfin_)));
          set_has_numtaskfin();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(80)) goto parse_numWorkSteal;
        break;
      }

      // optional int64 numWorkSteal = 10;
      case 10: {
        if (tag == 80) {
         parse_numWorkSteal:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &numworksteal_)));
          set_has_numworksteal();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(88)) goto parse_numWorkStealFail;
        break;
      }

      // optional int64 numWorkStealFail = 11;
      case 11: {
        if (tag == 88) {
         parse_numWorkStealFail:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &numworkstealfail_)));
          set_has_numworkstealfail();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(96)) goto parse_numTaskWait;
        break;
      }

      // optional int32 numTaskWait = 12;
      case 12: {
        if (tag == 96) {
         parse_numTaskWait:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int32, ::google::protobuf::internal::WireFormatLite::TYPE_INT32>(
                 input, &numtaskwait_)));
          set_has_numtaskwait();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(104)) goto parse_numTaskReady;
        break;
      }

      // optional int32 numTaskReady = 13;
      case 13: {
        if (tag == 104) {
         parse_numTaskReady:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int32, ::google::protobuf::internal::WireFormatLite::TYPE_INT32>(
                 input, &numtaskready_)));
          set_has_numtaskready();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(112)) goto parse_numCoreAvilable;
        break;
      }

      // optional int32 numCoreAvilable = 14;
      case 14: {
        if (tag == 112) {
         parse_numCoreAvilable:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int32, ::google::protobuf::internal::WireFormatLite::TYPE_INT32>(
                 input, &numcoreavilable_)));
          set_has_numcoreavilable();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(120)) goto parse_numAllCore;
        break;
      }

      // optional int32 numAllCore = 15;
      case 15: {
        if (tag == 120) {
         parse_numAllCore:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int32, ::google::protobuf::internal::WireFormatLite::TYPE_INT32>(
                 input, &numallcore_)));
          set_has_numallcore();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(128)) goto parse_outputsize;
        break;
      }

      // optional int64 outputsize = 16;
      case 16: {
        if (tag == 128) {
         parse_outputsize:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &outputsize_)));
          set_has_outputsize();
        } else {
          goto handle_unusual;
        }
        if (input->ExpectTag(136)) goto parse_submitTime;
        break;
      }

      // optional int64 submitTime = 17;
      case 17: {
        if (tag == 136) {
         parse_submitTime:
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int64, ::google::protobuf::internal::WireFormatLite::TYPE_INT64>(
                 input, &submittime_)));
          set_has_submittime();
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
  // @@protoc_insertion_point(parse_success:Value)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:Value)
  return false;
#undef DO_
}

void Value::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:Value)
  // required string id = 1;
  if (has_id()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->id().data(), this->id().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "Value.id");
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      1, this->id(), output);
  }

  // optional int64 indegree = 2;
  if (has_indegree()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(2, this->indegree(), output);
  }

  // repeated string parents = 3;
  for (int i = 0; i < this->parents_size(); i++) {
  ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
    this->parents(i).data(), this->parents(i).length(),
    ::google::protobuf::internal::WireFormat::SERIALIZE,
    "Value.parents");
    ::google::protobuf::internal::WireFormatLite::WriteString(
      3, this->parents(i), output);
  }

  // repeated string children = 4;
  for (int i = 0; i < this->children_size(); i++) {
  ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
    this->children(i).data(), this->children(i).length(),
    ::google::protobuf::internal::WireFormat::SERIALIZE,
    "Value.children");
    ::google::protobuf::internal::WireFormatLite::WriteString(
      4, this->children(i), output);
  }

  // repeated string dataNameList = 5;
  for (int i = 0; i < this->datanamelist_size(); i++) {
  ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
    this->datanamelist(i).data(), this->datanamelist(i).length(),
    ::google::protobuf::internal::WireFormat::SERIALIZE,
    "Value.dataNameList");
    ::google::protobuf::internal::WireFormatLite::WriteString(
      5, this->datanamelist(i), output);
  }

  // repeated int64 dataSize = 6;
  for (int i = 0; i < this->datasize_size(); i++) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(
      6, this->datasize(i), output);
  }

  // optional int64 allDataSize = 7;
  if (has_alldatasize()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(7, this->alldatasize(), output);
  }

  // optional int64 tasklength = 8;
  if (has_tasklength()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(8, this->tasklength(), output);
  }

  // optional int64 numTaskFin = 9;
  if (has_numtaskfin()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(9, this->numtaskfin(), output);
  }

  // optional int64 numWorkSteal = 10;
  if (has_numworksteal()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(10, this->numworksteal(), output);
  }

  // optional int64 numWorkStealFail = 11;
  if (has_numworkstealfail()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(11, this->numworkstealfail(), output);
  }

  // optional int32 numTaskWait = 12;
  if (has_numtaskwait()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt32(12, this->numtaskwait(), output);
  }

  // optional int32 numTaskReady = 13;
  if (has_numtaskready()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt32(13, this->numtaskready(), output);
  }

  // optional int32 numCoreAvilable = 14;
  if (has_numcoreavilable()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt32(14, this->numcoreavilable(), output);
  }

  // optional int32 numAllCore = 15;
  if (has_numallcore()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt32(15, this->numallcore(), output);
  }

  // optional int64 outputsize = 16;
  if (has_outputsize()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(16, this->outputsize(), output);
  }

  // optional int64 submitTime = 17;
  if (has_submittime()) {
    ::google::protobuf::internal::WireFormatLite::WriteInt64(17, this->submittime(), output);
  }

  if (_internal_metadata_.have_unknown_fields()) {
    ::google::protobuf::internal::WireFormat::SerializeUnknownFields(
        unknown_fields(), output);
  }
  // @@protoc_insertion_point(serialize_end:Value)
}

::google::protobuf::uint8* Value::SerializeWithCachedSizesToArray(
    ::google::protobuf::uint8* target) const {
  // @@protoc_insertion_point(serialize_to_array_start:Value)
  // required string id = 1;
  if (has_id()) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->id().data(), this->id().length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "Value.id");
    target =
      ::google::protobuf::internal::WireFormatLite::WriteStringToArray(
        1, this->id(), target);
  }

  // optional int64 indegree = 2;
  if (has_indegree()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt64ToArray(2, this->indegree(), target);
  }

  // repeated string parents = 3;
  for (int i = 0; i < this->parents_size(); i++) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->parents(i).data(), this->parents(i).length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "Value.parents");
    target = ::google::protobuf::internal::WireFormatLite::
      WriteStringToArray(3, this->parents(i), target);
  }

  // repeated string children = 4;
  for (int i = 0; i < this->children_size(); i++) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->children(i).data(), this->children(i).length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "Value.children");
    target = ::google::protobuf::internal::WireFormatLite::
      WriteStringToArray(4, this->children(i), target);
  }

  // repeated string dataNameList = 5;
  for (int i = 0; i < this->datanamelist_size(); i++) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->datanamelist(i).data(), this->datanamelist(i).length(),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "Value.dataNameList");
    target = ::google::protobuf::internal::WireFormatLite::
      WriteStringToArray(5, this->datanamelist(i), target);
  }

  // repeated int64 dataSize = 6;
  for (int i = 0; i < this->datasize_size(); i++) {
    target = ::google::protobuf::internal::WireFormatLite::
      WriteInt64ToArray(6, this->datasize(i), target);
  }

  // optional int64 allDataSize = 7;
  if (has_alldatasize()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt64ToArray(7, this->alldatasize(), target);
  }

  // optional int64 tasklength = 8;
  if (has_tasklength()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt64ToArray(8, this->tasklength(), target);
  }

  // optional int64 numTaskFin = 9;
  if (has_numtaskfin()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt64ToArray(9, this->numtaskfin(), target);
  }

  // optional int64 numWorkSteal = 10;
  if (has_numworksteal()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt64ToArray(10, this->numworksteal(), target);
  }

  // optional int64 numWorkStealFail = 11;
  if (has_numworkstealfail()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt64ToArray(11, this->numworkstealfail(), target);
  }

  // optional int32 numTaskWait = 12;
  if (has_numtaskwait()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt32ToArray(12, this->numtaskwait(), target);
  }

  // optional int32 numTaskReady = 13;
  if (has_numtaskready()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt32ToArray(13, this->numtaskready(), target);
  }

  // optional int32 numCoreAvilable = 14;
  if (has_numcoreavilable()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt32ToArray(14, this->numcoreavilable(), target);
  }

  // optional int32 numAllCore = 15;
  if (has_numallcore()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt32ToArray(15, this->numallcore(), target);
  }

  // optional int64 outputsize = 16;
  if (has_outputsize()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt64ToArray(16, this->outputsize(), target);
  }

  // optional int64 submitTime = 17;
  if (has_submittime()) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt64ToArray(17, this->submittime(), target);
  }

  if (_internal_metadata_.have_unknown_fields()) {
    target = ::google::protobuf::internal::WireFormat::SerializeUnknownFieldsToArray(
        unknown_fields(), target);
  }
  // @@protoc_insertion_point(serialize_to_array_end:Value)
  return target;
}

int Value::ByteSize() const {
  int total_size = 0;

  // required string id = 1;
  if (has_id()) {
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->id());
  }
  if (_has_bits_[1 / 32] & 194) {
    // optional int64 indegree = 2;
    if (has_indegree()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->indegree());
    }

    // optional int64 allDataSize = 7;
    if (has_alldatasize()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->alldatasize());
    }

    // optional int64 tasklength = 8;
    if (has_tasklength()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->tasklength());
    }

  }
  if (_has_bits_[8 / 32] & 65280) {
    // optional int64 numTaskFin = 9;
    if (has_numtaskfin()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->numtaskfin());
    }

    // optional int64 numWorkSteal = 10;
    if (has_numworksteal()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->numworksteal());
    }

    // optional int64 numWorkStealFail = 11;
    if (has_numworkstealfail()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->numworkstealfail());
    }

    // optional int32 numTaskWait = 12;
    if (has_numtaskwait()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int32Size(
          this->numtaskwait());
    }

    // optional int32 numTaskReady = 13;
    if (has_numtaskready()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int32Size(
          this->numtaskready());
    }

    // optional int32 numCoreAvilable = 14;
    if (has_numcoreavilable()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int32Size(
          this->numcoreavilable());
    }

    // optional int32 numAllCore = 15;
    if (has_numallcore()) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int32Size(
          this->numallcore());
    }

    // optional int64 outputsize = 16;
    if (has_outputsize()) {
      total_size += 2 +
        ::google::protobuf::internal::WireFormatLite::Int64Size(
          this->outputsize());
    }

  }
  // optional int64 submitTime = 17;
  if (has_submittime()) {
    total_size += 2 +
      ::google::protobuf::internal::WireFormatLite::Int64Size(
        this->submittime());
  }

  // repeated string parents = 3;
  total_size += 1 * this->parents_size();
  for (int i = 0; i < this->parents_size(); i++) {
    total_size += ::google::protobuf::internal::WireFormatLite::StringSize(
      this->parents(i));
  }

  // repeated string children = 4;
  total_size += 1 * this->children_size();
  for (int i = 0; i < this->children_size(); i++) {
    total_size += ::google::protobuf::internal::WireFormatLite::StringSize(
      this->children(i));
  }

  // repeated string dataNameList = 5;
  total_size += 1 * this->datanamelist_size();
  for (int i = 0; i < this->datanamelist_size(); i++) {
    total_size += ::google::protobuf::internal::WireFormatLite::StringSize(
      this->datanamelist(i));
  }

  // repeated int64 dataSize = 6;
  {
    int data_size = 0;
    for (int i = 0; i < this->datasize_size(); i++) {
      data_size += ::google::protobuf::internal::WireFormatLite::
        Int64Size(this->datasize(i));
    }
    total_size += 1 * this->datasize_size() + data_size;
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

void Value::MergeFrom(const ::google::protobuf::Message& from) {
  if (GOOGLE_PREDICT_FALSE(&from == this)) MergeFromFail(__LINE__);
  const Value* source =
    ::google::protobuf::internal::dynamic_cast_if_available<const Value*>(
      &from);
  if (source == NULL) {
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
    MergeFrom(*source);
  }
}

void Value::MergeFrom(const Value& from) {
  if (GOOGLE_PREDICT_FALSE(&from == this)) MergeFromFail(__LINE__);
  parents_.MergeFrom(from.parents_);
  children_.MergeFrom(from.children_);
  datanamelist_.MergeFrom(from.datanamelist_);
  datasize_.MergeFrom(from.datasize_);
  if (from._has_bits_[0 / 32] & (0xffu << (0 % 32))) {
    if (from.has_id()) {
      set_has_id();
      id_.AssignWithDefault(&::google::protobuf::internal::GetEmptyStringAlreadyInited(), from.id_);
    }
    if (from.has_indegree()) {
      set_indegree(from.indegree());
    }
    if (from.has_alldatasize()) {
      set_alldatasize(from.alldatasize());
    }
    if (from.has_tasklength()) {
      set_tasklength(from.tasklength());
    }
  }
  if (from._has_bits_[8 / 32] & (0xffu << (8 % 32))) {
    if (from.has_numtaskfin()) {
      set_numtaskfin(from.numtaskfin());
    }
    if (from.has_numworksteal()) {
      set_numworksteal(from.numworksteal());
    }
    if (from.has_numworkstealfail()) {
      set_numworkstealfail(from.numworkstealfail());
    }
    if (from.has_numtaskwait()) {
      set_numtaskwait(from.numtaskwait());
    }
    if (from.has_numtaskready()) {
      set_numtaskready(from.numtaskready());
    }
    if (from.has_numcoreavilable()) {
      set_numcoreavilable(from.numcoreavilable());
    }
    if (from.has_numallcore()) {
      set_numallcore(from.numallcore());
    }
    if (from.has_outputsize()) {
      set_outputsize(from.outputsize());
    }
  }
  if (from._has_bits_[16 / 32] & (0xffu << (16 % 32))) {
    if (from.has_submittime()) {
      set_submittime(from.submittime());
    }
  }
  if (from._internal_metadata_.have_unknown_fields()) {
    mutable_unknown_fields()->MergeFrom(from.unknown_fields());
  }
}

void Value::CopyFrom(const ::google::protobuf::Message& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void Value::CopyFrom(const Value& from) {
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool Value::IsInitialized() const {
  if ((_has_bits_[0] & 0x00000001) != 0x00000001) return false;

  return true;
}

void Value::Swap(Value* other) {
  if (other == this) return;
  InternalSwap(other);
}
void Value::InternalSwap(Value* other) {
  id_.Swap(&other->id_);
  std::swap(indegree_, other->indegree_);
  parents_.UnsafeArenaSwap(&other->parents_);
  children_.UnsafeArenaSwap(&other->children_);
  datanamelist_.UnsafeArenaSwap(&other->datanamelist_);
  datasize_.UnsafeArenaSwap(&other->datasize_);
  std::swap(alldatasize_, other->alldatasize_);
  std::swap(tasklength_, other->tasklength_);
  std::swap(numtaskfin_, other->numtaskfin_);
  std::swap(numworksteal_, other->numworksteal_);
  std::swap(numworkstealfail_, other->numworkstealfail_);
  std::swap(numtaskwait_, other->numtaskwait_);
  std::swap(numtaskready_, other->numtaskready_);
  std::swap(numcoreavilable_, other->numcoreavilable_);
  std::swap(numallcore_, other->numallcore_);
  std::swap(outputsize_, other->outputsize_);
  std::swap(submittime_, other->submittime_);
  std::swap(_has_bits_[0], other->_has_bits_[0]);
  _internal_metadata_.Swap(&other->_internal_metadata_);
  std::swap(_cached_size_, other->_cached_size_);
}

::google::protobuf::Metadata Value::GetMetadata() const {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::Metadata metadata;
  metadata.descriptor = Value_descriptor_;
  metadata.reflection = Value_reflection_;
  return metadata;
}


// @@protoc_insertion_point(namespace_scope)

// @@protoc_insertion_point(global_scope)
