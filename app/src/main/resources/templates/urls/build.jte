@importhexlet.code.util.NamedRoutes
@import hexlet.code.dto.BuildUrlPage;
@param BuildUrlPage page

@template.layout.page(

content = @`
    @if(page.getErrors() != null)
        <div class="mb-3">
            <ul>
                @for(var validator : page.getErrors().values())
                    @for(var error : validator)
                        <li>${error.getMessage()}</li>
                    @endfor
                @endfor
            </ul>
        </div>
    @endif

    <div class="mx-auto p-4 py-md-5">
        <form action="${NamedRoutes.urlsPath()}" method="post">
            <div class="mb-3">
                <label class="form-label">
                    Адрес
                    <input type="text" class="form-control" name="url" value="${page.getName()}" />
                </label>
            </div>
            <input type="submit" class="btn btn-primary" value="Сохранить" />
        </form>
    </div>
`,
)