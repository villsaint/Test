// $(document).ready(function () {
//         updateTable();
//         // createTable()
//     }
// );
//
// function updateTable() {
//     let userTable = $("#usersTable")
//
//     userTable.children().remove();
//
//     fetch("rest/all")
//         .then((response) => {
//             response.json().then(data => data.forEach(function (item, i, data) {
//                 let TableRow = createTable(item);
//                 userTable.append(TableRow);
//             }));
//         }).catch(error => {
//         console.log(error);
//     });
// }
//
// function createTable(user) {
//     let userRole = "";
//     for (let i = 0; i < user.roles.length; i++) {
//         userRole += " " + user.roles[i].role;
//     }
//     return `<tr>
//             <td>${user.id}</td>
//             <td>${user.name}</td>
//             <td>${user.lastname}</td>
//             <td>${user.age}</td>
//             <td>${user.email}</td>
//             <td>${user.username}</td>
//             <td>${user.password}</td>
//             <td>${userRole}</td>
//             <td>
//             <a  href="/rest/edit/${user.id}" class="btn btn-info eBtn" >Edit</a>
//             </td>
//             <td>
//             <a  href="/rest/delete/${user.id}" class="btn btn-danger delBtn">Delete</a>
//             </td>
//         </tr>`;
// }
//
// document.addEventListener('click', function (event) {
//     event.preventDefault()
//
//     if ($(event.target).hasClass('logout')) {
//         logout();
//     }
//
//     if ($(event.target).hasClass('user')) {
//         userInfo();
//     }
//
// });
//
// function logout() {
//     document.location.replace("/logout");
// }
//
// function userInfo() {
//     document.location.replace("/user");
// }

//Misha
// let dynamic = document.querySelector('.usersTable');
//
// const getUsersList = () => {
//     fetch('/rest/all')
//         .then(response => response.json())
//         .then(users => {
//             let li = '';
//             users.forEach(user => {
//                 li += `
//                 <tr>
//                     <td>${user.id}</td>
//                     <td>${user.name}</td>
//                     <td>${user.lastname}</td>
//                     <td>${user.age}</td>
//                     <td>${user.email}</td>
//                     <td>${user.username}</td>
//                     <td>${user.roles.map(role => role.role)}</td>
//                     <td><button class="btn btn-primary" onclick="editUser(${user.id})" data-toggle="modal" data-target="#edit-user-modal" data-action="edit" data-user-id="${user.id}" type="button">Edit</button></td>
//                     <td><button onclick="deleteUser(${user.id})" class="btn btn-danger"  data-action="delete" data-user-id="${user.id}" type="button">Delete</button></td>
//                 </tr>`
//             });
//
//             $("#usersTable").html(li);
//         });
// };
// getUsersList();

// PAC

$(document).ready(function () {
    restartAllUser();
    $('.AddBtn').on('click', function (event) {
        let user = {
            name: $("#firstName").val(),
            lastname: $("#lastName").val(),
            age: $("#age").val(),
            email: $("#email").val(),
            username: $("#userName").val(),
            // password: $("#password").val(),
            roles: getRole("#selectRole")

        }
        console.log(user);
        fetch("admin/rest/new", {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(user)
        }).then(() => openTabById('nav-home'))
            .then(() => restartAllUser());
        $('input').val('');
    });
});

function createTableRow(u) {
    let userRole = "";
    for (let i = 0; i < u.roles.length; i++) {
        userRole += " " + u.roles[i].role;
    }
    return `<tr id="user_table_row">
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.lastname}</td>
            <td>${u.age}</td>
            <td>${u.email}</td>
            <td>${u.username}</td>
            <td>${u.password}</td>
            <td>${userRole}</td>
            <td>
            <a  href="admin/rest/edit/${u.id}" class="btn btn-info eBtn" >Edit</a>
            </td>
            <td>
            <a  href="admin/rest/delete/${u.id}" class="btn btn-danger delBtn">Delete</a>
            </td>
        </tr>`;
}

function getRole(address) {
    let data = [];
    $(address).find("option:selected").each(function () {
        data.push(
            {id: $(this).val(), role: $(this).attr("name"), authority: $(this).attr("name")}
        )
    });
    return data;
}

function restartAllUser() {
    let UserTableBody = $("#user_table_body")

    UserTableBody.children().remove();

    fetch("admin/rest")
        .then((response) => {
            response.json().then(data => data.forEach(function (item, i, data) {
                let TableRow = createTableRow(item);
                UserTableBody.append(TableRow);

            }));
        }).catch(error => {
        console.log(error);
    });
}

document.addEventListener('click', function (event) {
    event.preventDefault()


    if ($(event.target).hasClass('delBtn')) {
        let href = $(event.target).attr("href");
        delModalButton(href)
    }



    if ($(event.target).hasClass('eBtn')) {
        let href = $(event.target).attr("href");
        $(".editUser #exampleModal").modal();

        $.get(href, function (user) {
            $(".editUser #id").val(user.id);
            $(".editUser #firstNameEd").val(user.firstName);
            $(".editUser #lastNameEd").val(user.lastName);
            $(".editUser #emailEd").val(user.email);
            $(".editUser #userNameEd").val(user.userName);
            $(".editUser #passwordEd").val(user.password);
            $(".editUser #selectRoleEd").val(user.roles);
        });
    }

    if ($(event.target).hasClass('editButton')) {
        let user = {
            id:$('#id').val(),
            firstName:$('#firstNameEd').val(),
            lastName:$('#lastNameEd').val(),
            email:$('#emailEd').val(),
            userName:$('#userNameEd').val(),
            password:$('#passwordEd').val(),
            roles: getRole("#selectRoleEd")

        }
        editModalButton(user)
        console.log(user);
    }

    if ($(event.target).hasClass('logout')) {
        logout();
    }
    if ($(event.target).hasClass('btnUserTable')) {
        userTable();
    }

});

function delModalButton(href) {
    fetch(href, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        }
    }).then(() => restartAllUser());
}

function editModalButton(user) {
    fetch("admin/rest/edit", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }).then(function (response) {
        $('input').val('');
        $('.editUser #exampleModal').modal('hide');
        restartAllUser();
    })
}

function openTabById(tab) {
    $('.nav-tabs a[href="#' + nav-home + '"]').tab('show');
}

function logout(){
    document.location.replace("/logout");
}

function userTable(){
    document.location.replace("/user");
}





