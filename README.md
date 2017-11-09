# SimpleJavaMail

[![作者](https://img.shields.io/badge/%E4%BD%9C%E8%80%85-KyonHuang-7AD6FD.svg)](http://kyonhuang.top)

* 用 Java 实现简单的收发邮件
* 发出的邮件包括文字和图片
* 对邮件的回复做最简单的情绪分析

## 具体步骤

1. 建立数据库，用一张表来存储收件人的邮箱地址（表结构见`doc/email.sql`）；
2. 使用`javax.mail`实现从自己的邮件地址发件；
3. 使用`javax.mail`实现收件；
4. 分析邮件回复，根据情绪结果打印对应的提示。

## 使用注意

你需要根据代码注释中的提示补齐或修改一些内容，比如发件人的邮箱地址和密码，以及对应的协议 host 等。

## License

Licensed under the [Apache 2.0 License](https://github.com/bighuang624/SimpleJavaMail/blob/master/LICENSE)