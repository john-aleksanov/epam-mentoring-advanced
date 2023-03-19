package com.epam.javamentoring.protocols.grpcserver.ping;

import io.grpc.stub.StreamObserver;

public class PingService extends PingServiceGrpc.PingServiceImplBase {

	@Override
	public void ping(PingRequest request, StreamObserver<PingResponse> responseObserver) {
		var requestMessage = request.getMessage();
		var response = PingResponse.newBuilder()
				.setMessage("Pong: " + requestMessage)
				.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}
