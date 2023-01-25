import pika

credentials = pika.PlainCredentials('demoadmin', 'secret123')
connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost', port='5672', credentials=credentials))
channel = connection.channel()
channel.exchange_declare('test', durable=True, exchange_type='topic')


def callbackFunctionForQueueHrque(ch, method, properties, body):
    print('Got a message from Queue hrque: ', body)


channel.basic_consume(queue='hrque', on_message_callback=callbackFunctionForQueueHrque, auto_ack=True)

channel.start_consuming()
