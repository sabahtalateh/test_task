По эндпоинту `/statistic` возвращается структура вида

```json
{
    "2019-12-02T23:38": {
        "cmd_1": 5
    },
    "2019-12-02T23:41": {
        "cmd_1": 2
    },
    "2019-12-02T23:42": {
        "cmd_1": 3,
        "cmd_2": 1
    }
}
```

Где ключи - минута за которую выводится статистика, значения - статистика за минуту