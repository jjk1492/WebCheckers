<!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        <meta http-equiv="refresh" content="10">
        <title>${title} | Web Checkers</title>
        <link rel="stylesheet" type="text/css" href="/css/style.css">
    </head>
    <body>
        <div class="page">

            <h1>Web Checkers</h1>

            <div class="navigation">
                <a href="/">my home</a>
                <#if signedIn && name??>
                    You're currently signed in as ${name}
                <#else >
                    <a href="/signin">sign in</a>
                </#if>
            </div>

            <div class="body">

                <p>Welcome to the world of online Checkers.</p>

                <#if message??>
                    <div id="message" class="${message.type}">
                        ${message.text}
                    </div>
                </#if>

                <#if !signedIn>
                    Currently signed in players: ${players?size}
                <#elseif players?size gt 1>
                    Other signed in players:
                    <ul>
                        <#list players as player>
                            <#if player != name>
                                <form id="${player}" action="./" method="POST">

                                    <input type="hidden" name="opponent" value=${player}>
                                    <a href="javascript:{}"
                                       onclick="document.getElementById
                                       ('${player}').submit();
                                       return false;">${player}</a>

                                </form>
                            </#if>
                        </#list>
                    </ul>
                <#else>
                    No other players signed in! :(
                </#if>
            </div>
        </div>
    </body>
</html>
