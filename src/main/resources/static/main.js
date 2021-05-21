$(document).ready(function () {
    restartTable();
});


function restartTable() {
    let tableBody = $("#allUsersTableBody")

    tableBody.children().remove();

    fetch("admin/rest/allusers")
        .then((response) => {
            response.json().then(data => data.forEach(function (item, i, data) {
                let table = createTable(item);
                tableBody.append(table);
            }));
        }).catch(error => {
        console.log(error);
    });
}

function createTable(user) {
    let userRoles = "";
    for (let i = 0; i < user.roles.length; i++) {
        userRoles += (" " + user.roles[i].role).replace('ROLE_','');
        if (i < user.roles.length - 1 ){
            userRoles += ' ,';
        }
    }
    return `<tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.username}</td>
            <td>${userRoles}</td>
            <td>
                <a  href="/admin/rest/${user.id}" class="btn btn-primary editBtn" >Edit</a>
            </td>
            <td>
                <a  href="/admin/rest/delete/${user.id}" class="btn btn-danger deleteBtn">Delete</a>
            </td>
        </tr>`;
}


function getRoles(){
    let role = [];
    if (document.getElementById('adminRole').checked || document.getElementById('adminRoleEdit').checked){
        role.push({id : 1, role : "ROLE_ADMIN"})
    }
    if (document.getElementById('userRole').checked || document.getElementById('userRoleEdit').checked){
        role.push({id : 2, role : "ROLE_USER"})
    }
    return role;
}


document.addEventListener('click', function (event) {
    event.preventDefault()

    if ($(event.target).hasClass('AddBtn')) {
        addUserButton()
    }

    if ($(event.target).hasClass('editBtn')) {
        let href = $(event.target).attr("href");
        $(".editUser #modalEdit").modal();

        $.get(href, function (user) {
            $(".editUser #id").val(user.id);
            $(".editUser #nameEdit").val(user.name);
            $(".editUser #lastNameEdit").val(user.lastName);
            $(".editUser #ageEdit").val(user.age);
            $(".editUser #emailEdit").val(user.email);
            $(".editUser #usernameEdit").val(user.username);
            $(".editUser #passwordEdit").val(user.password);
        });
    }

    if ($(event.target).hasClass('editBtnCommit')) {
        let user = {
            id: $('#id').val(),
            name: $('#nameEdit').val(),
            lastName: $('#lastNameEdit').val(),
            age: $('#ageEdit').val(),
            email: $('#emailEdit').val(),
            username: $('#usernameEdit').val(),
            password: $('#passwordEdit').val(),
            roles: getRoles()

        }
        editUser(user)
        console.log(user);
    }

    if ($(event.target).hasClass('deleteBtn')) {
        let href = $(event.target).attr("href");
        deleteUser(href)
    }

    if ($(event.target).hasClass('userPnl')) {
        document.location.replace("/user");
    }

    if ($(event.target).hasClass('adminPnl')) {
        document.location.replace("/admin");
    }

    if ($(event.target).hasClass('logout')) {
        document.location.replace("/logout");
    }

});

function addUserButton(){
    let user = {
        name: $("#name").val(),
        lastName: $("#lastName").val(),
        age: $("#age").val(),
        email: $("#email").val(),
        username: $("#username").val(),
        password: $("#password").val(),
        roles: getRoles()
    }
    console.log(user)
    fetch("admin/rest/newUser", {
        credentials: 'include',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
        .then(() => document.location.replace("/admin"))
        .then(() => restartTable())
}

function deleteUser(href) {
    fetch(href, {
        credentials: 'include',
        method: "DELETE",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        }
    })
        .then(() => restartTable());
}

function editUser(user) {
    fetch("admin/rest/edit", {
        credentials: 'include',
        method: "PUT",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }).then(function (response) {
        $('input').val('');
        $('.editUser #modalEdit').modal('hide');
        restartTable();
    })
}
