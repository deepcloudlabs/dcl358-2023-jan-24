//region Consuming Binance's REST on HTTP Api using Fetch Api
//region Kafka Producer
const {Kafka} = require("kafkajs");
const kafka = new Kafka({
    clientId: "trade-consumer",
    brokers: ['localhost:9092']
});

const consumer = kafka.consumer({
    "groupId": "trade-consumer"
});

consumer.connect().then(()=>{
    console.log("Connected to the kafka server.");
    consumer.subscribe({topic: "hr-events", fromBeginning: true}).then(() =>{
        consumer.run({
            eachMessage: async ({topic, partition, message}) => {
                console.log(`Received message from ${partition}:${topic}.`);
                console.log(`Message key: ${message.key}.`);
                let event = JSON.parse(message.value);
                console.log(`"kafka: ${message.value}`);
            }
        });
    })
});
//endregion

//region Consuming Binance's REST on WebSocket Api
const websocket = require('ws');
const restWsUrl = "ws://localhost:4001/hr/api/v1/events";
const ws = new websocket(restWsUrl);
ws.on('message', frame => {
   const hrEvent = JSON.parse(frame);
   console.log(`ws: ${frame}`);
});
//endregion



