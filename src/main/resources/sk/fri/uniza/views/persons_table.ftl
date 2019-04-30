<#-- @ftlvariable name="" type="sk.fri.uniza.views.PersonsView" -->
<!-- calls getPersons().getName() and sanitizes it -->
<div class="section no-pad-bot" id="index-banner">
    <div class="container">
        <table id="example" class="striped" style="width:100%">
            <thead>
            <tr>
                <th>Meno</th>
                <th>Priezvisko</th>
                <th>e-mail</th>
                <th>Prihlasovacie meno</th>
                <th>Roles</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <#list getPersons() as person>
                <tr>
                    <td>
                        ${person.firstName}
                    </td>
                    <td>
                        ${person.lastName}
                    </td>
                    <td>
                        ${person.email}
                    </td>
                    <td>
                        ${person.name}
                    </td>
                    <td>
                        ${person.rolesString}
                    </td>
                    <td>
                        <a href="/persons/user-info?id=${person.getId()}" class="btn waves-effect waves-light grey"
                           name="action">
                            <i class="material-icons">edit</i>
                        </a>
                        <#if person.getId() != loginUser.getId()>
                        <a onclick="onDelete('/persons/user-delete?id=${person.getId()}&page=${paged.page}')"
                           class="btn waves-effect waves-light red " name="action">
                            <i class="material-icons">delete_forever</i>
                            </#if>
                        </a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
        <ul class="pagination">
            <#if paged.prevPage?? >
                <li class="waves-effect"><a href="?page=${paged.prevPage}">
                        <i class="material-icons">chevron_left</i></a></li>
            <#else>
                <li class="disabled"><a href="#!"><i class="material-icons">chevron_left</i></a></li>
            </#if>

            <#list 1..paged.lastPage as pageNum>
                <#if pageNum == paged.page>
                    <li class="active"><a href="?page=${pageNum}">${pageNum}</a></li>
                <#else>
                    <li class="waves-effect"><a href="?page=${pageNum}">${pageNum}</a></li>
                </#if>
            </#list>

            <#if paged.nextPage?? >
                <li class="waves-effect"><a href="?page=${paged.nextPage}">
                        <i class="material-icons">chevron_right</i></a></li>
            <#else>
                <li class="disabled"><a href="#!"><i class="material-icons">chevron_right</i></a></li>
            </#if>
        </ul>
    </div>
    <br><br>
</div>
<!-- Modal Trigger -->
<#--<a class="waves-effect waves-light btn modal-trigger" href="#modal1">Modal</a>-->

<!-- Modal Structure -->
<div id="modal1" class="modal">
    <div class="modal-content">
        <h4>Chcete vymazať užívateľa?</h4>
        <p></p>
    </div>
    <div class="modal-footer">
        <a href="#!" class="modal-close waves-effect waves-red btn-flat">Nie</a>
        <a id="modal_href" href="#!" class="modal-close waves-effect waves-green btn-flat">Áno</a>
    </div>
</div>

<script>
    function onDelete(url) {
        $("#modal_href").attr("href", url);
        // var elem = document.querySelector("#modal1")
        // var instance = M.Modal.getInstance(elem);
        // instance.open();
        //
        var elem = document.querySelector("#modal1");
        var instance = M.Modal.init(elem);

        instance.open();


    }
</script>