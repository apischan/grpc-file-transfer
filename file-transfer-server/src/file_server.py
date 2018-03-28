from concurrent import futures

import file_download_async_pb2_grpc as stub
import file_pb2 as data
import grpc
import time

_ONE_DAY_IN_SECONDS = 60 * 60 * 24
_CHUNK_SIZE = 100

class FileDownloadServiceServicer(stub.FileDownloadServiceServicer):
    def Download(self, request, context):
        file = open(request.filePath, "r")
        file_content = file.read()

        pass
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def _batches(self, file_content):
        chunked_content = map(''.join, zip(*[iter(file_content)] * _CHUNK_SIZE))
        elems = len(chunked_content)
        for n in range(elems):
            if n == elems:
                yield data.FileBatch(chunked_content[n], True)
            yield data.FileBatch(chunked_content[n], False)

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    stub.add_FileDownloadServiceServicer_to_server(
        FileDownloadServiceServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    try:
        while True:
            time.sleep(_ONE_DAY_IN_SECONDS)
    except KeyboardInterrupt:
        server.stop(0)

if __name__ == '__main__':
    serve()

