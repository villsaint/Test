$(document).ready(function () {

    createTableRow(userI);

});
function createTableRow(user) {
    fetch("admin/rest/"+user.id)
    let userRole = "";
    for (let i = 0; i < user.roles.length; i++) {
        userRole += (" " + user.roles[i].role).replace('ROLE_','');
        if (i < user.roles.length - 1 ){
            userRole += ' ,';
        }
    }
    return `<tr id="user_table_row">
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.username}</td>
            <td>${userRole}</td>
        </tr>`;
}