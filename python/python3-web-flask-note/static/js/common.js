new Vue({
    el: '#app',
    data: function() {
      return {
            noteList: [{
                id: null,
                title: '',
                content: '',
                create_time: '',
                username: ''
            }],
            note : {
                title: '',
                content: '',
                create_time: '',
            }
      }
    },
    created: function() {
        this.search();
    },
    methods: {
        search: function() {
            var vm = this;
            $.ajax({
                url: "/note/list",
                type: "GET",
                success: function (result) {
                    if (result) {
                        vm.noteList = result;
                        console.log(vm.noteList);
                    }
                }
            });
        },
        login: function () {
            var username = prompt("请输入用户名", "");
            // 验证登录，模拟认证
            if (username){
                window.location.href="/note/index"
            }
        },
        add: function(){
            var vm = this;
             $.ajax({
                url: "/note/add",
                type: "GET",
                data: vm.note,
                success: function (result) {
                    if (result) {
                       alert(result.message);
                       vm.search();
                    }
                }
            });
        },
        detail: function (index) {
            var vm = this;
            var id = vm.noteList[index].id;
            window.location.href="/note/detail/" + id;
        },
        deleteNote: function (index) {
            var vm = this;
            var id = vm.noteList[index].id
            $.ajax({
                url: "/note/delete",
                type: "POST",
                data: {"id": id},
                success: function (result) {
                    alert(result.message);
                }
            });
        },

    }
});

