package org.junnikym

import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class SampleService: SampleServiceGrpc.SampleServiceImplBase() {
    override fun hello(request: HelloRequest?, responseObserver: StreamObserver<HelloResponse>?) {
        val reply = HelloResponse.newBuilder()
            .setMessage("Hello! ${request?.name ?: "No name"}")
            .build()

        responseObserver!!.onNext(reply)
        responseObserver.onCompleted()
    }
}