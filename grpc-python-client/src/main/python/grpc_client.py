#!/usr/bin/env python3

import grpc
from ping_pb2 import PingRequest, PingResponse
from ping_pb2_grpc import PingServiceStub


def run():
    channel = grpc.insecure_channel('localhost:50051')
    stub = PingServiceStub(channel)
    request = PingRequest(message="Hello from the Python gRPC Client!")
    response = stub.ping(request)
    print("Received response:", response.message)


if __name__ == "__main__":
    run()
