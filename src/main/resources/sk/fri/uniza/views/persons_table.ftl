<#-- @ftlvariable name="" type="sk.fri.uniza.views.PersonsView" -->
<!-- calls getPersons().getName() and sanitizes it -->
<div class="section no-pad-bot" id="index-banner">
    <div class="container">
        <table id="example" class="striped responsive-table" style="width:100%">
            <thead>
            <tr>
                <th>Meno</th>
                <th>Priezvisko</th>
                <th>e-mail</th>
                <th>Prihlasovacie meno</th>
                <th>Roles</th>
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
                </tr>
            </#list>
            </tbody>

        </table>
    </div>
    <br><br>
</div>