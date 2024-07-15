@import hexlet.code.dto.UrlPage

@param UrlPage page

@template.layout.page(

content = @`
    <div class="mb-3">
        <h1>${page.getUrl().toString()}</h1>
    </div>
`,
)