System表中，共需要增加五个参数
HUAWEIYUN_SMS_APP_KEY		短信应用的appkey
HUAWEIYUN_SMS_APP_SECRET	短信应用的appSecret
HUAWEIYUN_SMS_SENDER		国内短信签名通道号或国际/港澳台短信通道号
HUAWEIYUN_SMS_SIGNATURE		签名
HUAWEIYUN_SMS_TEMPLATE_ID_CREATESITE	开通网站的验证码模版id

使用：
smsService.sendByHuaweiSMS(request, Global.huaweiSmsUtil, "58972990fb1b4b16abf312d991a00eee", "17076000000", SmsLog.TYPE_LOGIN);