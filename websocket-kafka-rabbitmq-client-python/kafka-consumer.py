import json

from kafka import KafkaConsumer

consumer = KafkaConsumer(
    "hr-events",
    bootstrap_servers=["localhost:9092"],
    group_id="hr-consumer-python",
    auto_offset_reset='earliest',
    enable_auto_commit=True,
    value_deserializer=lambda m: json.loads(m.decode('utf-8'))
)

for message in consumer:
    print(message.value)
