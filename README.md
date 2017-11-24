## brickstore
### Package and run
build
`$ mvn package`
run
`$ mvn java -jar ./target/brickstore-0.0.1-SNAPSHOT.jar`

### Requirements
#### Stage 1 (GET)

#### Stage 3 (POST)
Personally I believe in not using verbs as part of the URI in a REST system however, because of the requirement states it should send 400 (Bad Request), then I assume it's a verb uri like `POST /customers/{customerId}/orders/{orderId}/dispatch`. This will set `dispatchValue` to `true`.

If it was not the case, I would put dispatch action part of the resource update in a normal PATCH request.


