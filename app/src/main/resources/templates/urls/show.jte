@import hexlet.code.dto.UrlPage
@import hexlet.code.util.NamedRoutes

@param UrlPage page

@template.layout.page(
page = page,
content = @`
    <div class="mb-3">
        <h1>${page.getUrl().getName()}</h1>
    </div>

    <h2 class="mt-5">Проверки</h2>
    <div class="mx-auto p-4 py-md-5">
        <form action="${NamedRoutes.urlChecksPath(page.getUrl().getId())}" method="post">
            <input type="submit" class="btn btn-primary" value="Запустить проверку"/>
        </form>
    </div>

    <table class="table table-striped">
        <thead>
        <tr>
            <th class="col-1">ID</th>
            <th class="col-1">Код ответа</th>
            <th>title</th>
            <th>h1</th>
            <th>description</th>
            <th class="col-2">Дата проверки</th>
        </tr>
        </thead>
        <tbody>
        @if(page.getCheckList() != null)
            @for(var check : page.getCheckList())
                <tr>
                    <td>
                        ${check.getId()}
                    </td>
                    <td>
                        ${check.getStatusCode()}
                    </td>
                    <td>
                        ${check.getTitle()}
                    </td>
                    <td>
                        ${check.getH1()}
                    </td>
                    <td>
                        ${check.getDescription()}
                    </td>
                    <td>
                        ${check.dateToString()}
                    </td>
                </tr>
            @endfor
        @endif
        </tbody>
    </table>
`
)