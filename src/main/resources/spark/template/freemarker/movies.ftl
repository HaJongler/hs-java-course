<html>
    <head>
        <title>Movies Library</title>
        <link rel="stylesheet" href="/style.css">
    </head>
    <body>
        <header>Movies Library</header>

        <#list movies as movie>
            <section>
                <p>${movie}</p>
                <a class="delete" href="#">Delete</a>
            </section>
        </#list>

        <footer>Java Programming - Harbour.Space University</footer>
    </body>
</html>