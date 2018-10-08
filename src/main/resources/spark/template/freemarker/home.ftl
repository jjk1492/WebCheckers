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
                <#if signedIn>
                    You're currently signed in as ${playerName}
                <#else >
                    <a href="/signin">sign in</a>
                </#if>
            </div>

            <div class="body">
                <p>Welcome to the world of online Checkers.</p>

                <#if players?size gt 1 >
                    Currently signed in players: <#if !signedIn>${players?size}</#if>
                        <#if signedIn>
                            <ul>
                                <#list players as player>
                                  <#if player != playerName>
                                     <li>${player}</li>
                                </#if>
                                </#list>
                            </ul>
                        </#if>
                </#if>
            </div>

        </div>
    </body>
</html>
