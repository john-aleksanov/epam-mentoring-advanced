package com.epam.javamentoring.protocols.grpcjavaclient;

import com.epam.javamentoring.protocols.grpcjavaclient.ping.PingRequest;
import com.epam.javamentoring.protocols.grpcjavaclient.ping.PingResponse;
import com.epam.javamentoring.protocols.grpcjavaclient.ping.PingServiceGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;

public class GrpcJavaClient {

	private PingServiceGrpc.PingServiceBlockingStub pingServiceBlockingStub;

	public  GrpcJavaClient(Channel channel) {
		pingServiceBlockingStub = PingServiceGrpc.newBlockingStub(channel);
	}

	public static void main(String[] args) {
		var channel = ManagedChannelBuilder.forTarget("localhost:50051")
				.usePlaintext()
				.build();
		var gRpcJavaClient = new GrpcJavaClient(channel);
		var requestMessage = "Hello from the Java gRPC Client!";
		var response = gRpcJavaClient.getPingResponse(requestMessage);
		System.out.println(response);
	}

	public PingResponse getPingResponse(String message) {
		var request = PingRequest.newBuilder()
				.setMessage(message)
				.build();
		return pingServiceBlockingStub.ping(request);
	}
}
