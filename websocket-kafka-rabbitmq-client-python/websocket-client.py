import asyncio
import json

import websockets


async def consumer_handler(frames):
    async for frame in frames:
        trade = json.loads(frame);
        print(trade)


async def connect():
    async with websockets.connect("ws://localhost:4001/hr/api/v1/events") as ws:
        await consumer_handler(ws)


asyncio.run(connect())
