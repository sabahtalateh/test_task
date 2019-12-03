const sse = new EventSource('http://localhost:8080/statistic');
sse.onmessage = function (evt) {
    const now = new Date();
    console.log(`SSE Received at: ${now.getHours()}:${now.getMinutes()}:${now.getSeconds()}`);

    const body = JSON.parse(evt.data);
    for (let key in body) {
        console.log(`Stat for minute: ${key}`);
        const commands = body[key];
        if (0 === Object.values(commands).length) {
            console.log('No commands');
        } else {
            for (let cmd in commands) {
                const count = commands[cmd];
                console.log(`${cmd} : ${count}`);
            }
        }
    }

    console.log('=============================')
};