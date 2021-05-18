$(document).ready(function () {
    updateTable();
    $('.addUserButton').on('click', function (event) {
        let user = {
            name: $("#newName").val(),
            lastname: $("#newLastName").val(),
            age: $("#newAge").val(),
            email: $("#newEmail").val(),
            userName: $("#newUsername").val(),
            password: $("#newPassword").val(),
            roles: getRoles()
        }
        console.log(user);
        fetch("rest/new", {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(user)
        })
            .then(() => $('.nav-tabs a[href="#table"]').tab('show'))
            .then(() => updateTable());
        $('input').val('');
    });
});

function getRoles(address) {
    let role = [];
    // if (document.getElementById('newAdminRole').clicked == true) {
    //     role.push({id: 1, role: "ROLE_ADMIN"})
    // }
    // if (document.getElementById('newUserRole').clicked == true) {
    //     role.push({id: 2, role: "ROLE_USER"})
    // }
    $(address).find("option:selected").each(function () {
        data.push(
            {id: $(this).val(), role: $(this).attr("name")}
        )
    });
    return role;
}

function updateTable() {
    let userTable = $("#usersTable")

    userTable.children().remove();

    fetch("rest/all")
        .then((response) => {
            response.json().then(data => data.forEach(function (item, i, data) {
                let TableRow = createTable(item);
                userTable.append(TableRow);

            }));
        }).catch(error => {
        console.log(error);
    });
}

function createTable(usr) {
    let userRole = "";
    for (let i = 0; i < usr.roles.length; i++) {
        userRole += " " + usr.roles[i].role;
    }
    return `<tr>
            <td>${usr.id}</td>
            <td>${usr.name}</td>
            <td>${usr.lastName}</td>
            <td>${usr.age}</td>
            <td>${usr.email}</td>
            <td>${usr.username}</td>
            <td>${usr.password}</td>
            <td>${userRole}</td>
            <td>
            <a  href="/rest/edit/${usr.id}" class="btn btn-info eBtn" >Edit</a>
            </td>
            <td>
            <a  href="/rest/delete/${usr.id}" class="btn btn-danger delBtn">Delete</a>
            </td>
        </tr>`;
}

document.addEventListener('click', function (event) {
    event.preventDefault()

    if ($(event.target).hasClass('editUser')) {
        let href = $(event.target).attr("href");
        $(".editUser #editModal").modal();

        $.get(href, function (user) {
            $(".editUser #id").val(user.id);
            $(".editUser #editName").val(user.name);
            $(".editUser #editLastname").val(user.lastname);
            $(".editUser #editAge").val(user.age);
            $(".editUser #editEmail").val(user.email);
            $(".editUser #editUsername").val(user.username);
            $(".editUser #editPassword").val(user.password);
            $(".editUser #editRole").val(user.roles);
        });
    }

    if ($(event.target).hasClass('editUserCommit')) {
        let user = {
            id:$('#id').val(),
            name:$('#editName').val(),
            lastname:$('#editLastname').val(),
            age:$('#editAge').val(),
            email:$('#editEmail').val(),
            username:$('#editUsername').val(),
            password:$('#editPassword').val(),
            //hz
            roles: getRoles("#editRole")

        }
        editUser(user)
        console.log(user);
    }

    if ($(event.target).hasClass('deleteUser')) {
        let href = $(event.target).attr("href");
        deleteUser(href)
    }


    if ($(event.target).hasClass('logout')) {
        logout();
    }

    if ($(event.target).hasClass('userPanelBtn')) {
        userInfo();
    }

});



function deleteUser(href) {
    fetch(href, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        }
    }).then(() => updateTable());
}

function editUser(user) {
    fetch("rest/edit/" + user.id, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }).then(function (response) {
        $('input').val('');
        $('.editUser #exampleModal').modal('hide');
        updateTable();
    })
}

function logout(){
    document.location.replace("/logout");
}

function userInfo(){
    document.location.replace("/user");
}
