# Overview
This branch showcases a very simple gRPC Java server and two gRPC clients - written in Java and Python. The Java server
and client are bundled into a multi-module Gradle project.
To run the server and test the clients:
1. Build the parent Gradle project running this command from the root:


>./gradlew build
   
This will download all required dependencies and generate stubs for the Java classes
using the proto compiler for the server and the Java client.

2. To run the server, run the <code>main</code> method from the <code>GrpcServer</code> class in the Java server module.
You should see 'Server started' in the console.

3. To run the Java client, run the <code>main</code> method from the <code>GrpcJavaClient</code> class in the
Java client module. You should see 'message: "Pong: Hello from the Java gRPC Client!"' in the console.

4. To run the Python client:

Firstly, install the required dependencies:
> pip3 install grpcio grpcio-tools

From the <code>grpc-python-client/src/main/proto</code> folder run
> python3 -m grpc_tools.protoc --python_out=../python --grpc_python_out=../python --proto_path=. ./ping.proto

to generate stubs for the messages and the service in the proto file.

Finally, from the <code>python</code> folder, run
> python3 grpc_client.py

You should see 'Received response: Pong: Hello from the Python gRPC Client!' in the console.