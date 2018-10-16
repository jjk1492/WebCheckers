<!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        <meta http-equiv="refresh" content="10">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
    </head>
    <body>
        <div class="page">

            <h1>Web Checkers</h1>

            <div class="navigation">
                <a href="/">my home</a>
            </div>

            <div class="body">
                <div class="${messageType}">${message}</div>
                <form action="./signin" method="POST">
                    <input name="name">
                    <br>
                    <button type="submit">Sign In</button>
                </form>
            </div>

        </div>
    </body>
</html>
