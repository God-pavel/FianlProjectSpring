<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>


<@c.page>
    <h3><@spring.message "page.user"/></h3>

    <form action="/user" method="post">
    <input type="text" name="username" value="${user.username}">
    <#list roles as role>
        <div>
        <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
        </div>
    </#list>
<input type="hidden" name="userId" value="${user.id}">
<input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit"><@spring.message "merchandise.save"/></button>
    </form>
</@c.page>