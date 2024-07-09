@import hexlet.code.util.NamedRoutes
@template.layout.page(
content = @`
    <h2>Welcome to Site Analyzer</h2>

    <a href="${NamedRoutes.urlsPath()}">Все адреса</a>
`
)