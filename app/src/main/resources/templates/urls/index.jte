@importhexlet.code.util.NamedRoutes
@import hexlet.code.dto.UrlsPage
@param UrlsPage page

@template.layout.page(
content = @`
    <h1>Все адреса</h1>
    <div class="mb-3">
        <a href="${NamedRoutes.buildUrlPath()}">Добавить новый адрес</a>
    </div>
    <table class="table table-striped">
        @for(var url : page.getUrls())
            <tr>
                <td>
                    ${url.getId()}
                </td>
                <td>
                    <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
                </td>
                <td>
                    ${url.toString()}
                </td>
            </tr>
        @endfor
    </table>
`,
)