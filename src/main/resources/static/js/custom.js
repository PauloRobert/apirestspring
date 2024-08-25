var apiUrl;
if (window.location.hostname === "localhost") {
    apiUrl = "http://localhost:8080";
} else {
    apiUrl = "https://apirestcadastro.azurewebsites.net";
}

function atualizarTabela() {
    $.getJSON(apiUrl + "/api/usuario/listar", function(data) {
        if (data.length === 0) {
            $("#mensagemtabelaatualizada").html("Não há clientes cadastrados.").fadeIn('slow');
            setTimeout(function() {
                $("#mensagemtabelaatualizada").fadeOut('slow');
            }, 3000);
            return;
        }

        var tabela = document.getElementById("tabela-clientes");
        tabela.innerHTML = "";
        data.forEach(cliente => {
            tabela.innerHTML +=
                `<tr>
                    <td>${cliente.codigo}</td>
                    <td>${cliente.nome}</td>
                    <td>${cliente.login}</td>
                    <td>${cliente.senha}</td>
                    <td>${cliente.dataHoraCadastro}</td>
                    <td><button class='btn btn-danger btn-sm' onClick='deletarUsuario(${cliente.codigo})'>Deletar</button></td>
                </tr>`;
        });
    }).fail(function(jqXHR, textStatus, errorThrown) {
        $("#mensagemtabelaatualizada").html("Erro ao atualizar tabela: " + errorThrown).fadeIn('slow');
        setTimeout(function() {
            $("#mensagemtabelaatualizada").fadeOut('slow');
        }, 3000);
    });
}

function cadastrarCliente() {
    var nome = $("#nome").val().trim();
    var login = $("#login").val().trim();
    var senha = $("#senha").val().trim();

    if (nome === "" || login === "" || senha === "") {
        $(".form-control").addClass("is-invalid");
        return false;
    } else if (nome.length > 50) {
        $("#nome").addClass("is-invalid");
        return false;
    } else if (login.length > 50) {
        $("#login").addClass("is-invalid");
        return false;
    } else if (senha.length < 10 || senha.length > 20) {
        $("#senha").addClass("is-invalid");
        return false;
    } else {
        $(".form-control").removeClass("is-invalid");
    }

    var dados = {nome: nome, login: login, senha: senha};

    $.ajax({
        url: apiUrl + "/api/usuario/cadastrar",
        type: "POST",
        data: JSON.stringify(dados),
        contentType: "application/json",
        success: function(result, status, xhr) {
            if(xhr.status === 200) {
                $("#nome").val("");
                $("#login").val("");
                $("#senha").val("");
                $("#modalCadastro").modal("hide");
                $("#mensagem").html("Cadastro realizado com sucesso.").fadeIn();
                $("#mensagem").fadeOut('slow');
                atualizarTabela();
            }
        },
        error: function(xhr, status, error) {
            alert("Erro ao salvar usuário: " + error);
        }
    });
}

function deletarUsuario(codigo) {
    if (confirm("Tem certeza que deseja deletar o usuário?")) {
        $.ajax({
            url: apiUrl + "/api/usuario/deletar/" + codigo,
            type: "DELETE",
            success: function() {
                // Remover a linha do cliente deletado diretamente da tabela
                $("#tabela-clientes tr").each(function() {
                    var row = $(this);
                    if (row.find("td:first").text() == codigo) {
                        row.remove();  // Remove a linha da tabela
                    }
                });

                // Mostrar mensagem de sucesso
                $("#mensagem").text("Usuário deletado com sucesso.").show().delay(3000).fadeOut();
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $("#mensagem").text("Erro ao deletar usuário: " + textStatus).show().delay(3000).fadeOut();
            }
        });
    }
}