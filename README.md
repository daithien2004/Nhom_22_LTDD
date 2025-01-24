Thay đổi các thông số sau trong application.properties để test.

spring.mail.username=daithien2004@gmail.com
spring.mail.password=${MAIL_PASSWORD}


* Đăng ký
http://localhost:8080/api/register
{
    "email": "conco2444@gmail.com",
    "password": "123"
}

* Kích hoạt
http://localhost:8080/api/activate
{
    "email": "conco2444@gmail.com",
    "otp": "135332"
}

* Đăng nhập
http://localhost:8080/api/login
{
    "email": "conco2444@gmail.com",
    "password": "123"
}

* Quên mật khẩu
http://localhost:8080/api/forgot-password
{
    "email": "conco2444@gmail.com"
}

* Đặt lại mật khẩu
http://localhost:8080/api/reset-password
{
    "email": "conco2444@gmail.com",
    "otp": "416164",
    "newPassword": "111"
}
