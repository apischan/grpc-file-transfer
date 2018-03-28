from grpc_tools import protoc

protoc.main((
    '',
    '-I../proto',
    '--python_out=../../../generated/main/python',
    '--grpc_python_out=../../../generated/main/python',
    '../proto/file_download_async.proto',
    '../proto/file.proto',
))