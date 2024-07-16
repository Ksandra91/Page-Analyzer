@import hexlet.code.dto.UrlPage
@import hexlet.code.util.NamedRoutes

@param UrlPage page

@template.layout.page(

content = @`
    <div class="mb-3">
        <h1>${page.getUrl().toString()}</h1>
    </div>

    <div class="mx-auto p-4 py-md-5">
        <form action="${NamedRoutes.urlChecksPath(page.getUrl().getId())}" method="post">
            <input type="submit" class="btn btn-primary" value="Запустить проверку"/>
        </form>
    </div>
`,
)