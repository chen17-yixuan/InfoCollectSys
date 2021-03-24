var vm = new Vue({

    el: '#vue_det',

    data: {
        site: "等待",
        uploadstate: "等待上传",
        uploadstateclass: false,
        pmcheckanddeleteclass: false,
        pmcheckanddelete: "等待操作",
        datecontent: null,
        county: null,
        dwname: null,
        hkname: null,
        sbip: null,
        wbdw: null,
        gzfl: null,
        fxpt: null,
        sfxz: null,
        checkedall: "checked",
        checkedids: [],
        errinfo: {
            errortableId: null,
            errortableCounty: null,
            errortableDianweiname: null,
            errortableHaikangpname: null,
            errortableHaixinpname: null,
            errortableDeviceType: null,
            errortableDeviceIp: null,
            errortableBuildCompany: null,
            errortableDeviceExpriation: null,
            errortableRepairStatus: null,
            errortableRequestTime: null,
            errortableNote: null,
            errortableServercompany: null,
            errortableFaultclassification: null,
            errortableBelongPaltform: null,
            errortableNewcreate: null,
        }
    },
    methods: {


        deletebyids: function (singleid) {
            $('#updateinfoModal').modal('hide');

            if (confirm('确定删除？')) {
                let formData = new FormData();
                //file对应传过去的参数
                formData.append('deldata', JSON.stringify(this.checkedids));

                if (singleid != "null") {
                    formData.append('singleid', singleid);
                } else {
                    this.checkedids = [];
                }

                axios({
                    method: 'post',
                    url: "/daywork/deletetablevalue",
                    data: formData,
                    headers: {
                        //文件头必须写，这是网址头
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                })
                    .then(res => {
                        $('#updateinfoModal').modal('hide');

                        this.checkedids = [],

                            this.search();
                    })
                    .catch(err => {
                        console.log("失败");
                    });
            } else {
                $('#updateinfoModal').modal('hide');
            }


        },

        updatebyid: function () {

            let formData = new FormData();
            //file对应传过去的参数
            formData.append('updatedata', JSON.stringify(this.errinfo));


            axios({
                method: 'post',
                url: "/daywork/updatetablevalue",
                data: formData,
                headers: {
                    //文件头必须写，这是网址头
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
                .then(res => {
                    this.search();
                    $('#updateinfoModal').modal('hide');

                })
                .catch(err => {
                    console.log("失败");
                });
        },

        updateinfoModal: function (datec) {
            this.errinfo.errortableId = datec.errortableId;
            this.errinfo.errortableCounty = datec.errortableCounty;
            this.errinfo.errortableDianweiname = datec.errortableDianweiname;
            this.errinfo.errortableHaikangpname = datec.errortableHaikangpname;
            this.errinfo.errortableHaixinpname = datec.errortableHaixinpname;
            this.errinfo.errortableDeviceType = datec.errortableDeviceType;
            this.errinfo.errortableDeviceIp = datec.errortableDeviceIp;
            this.errinfo.errortableBuildCompany = datec.errortableBuildCompany;
            this.errinfo.errortableDeviceExpriation = datec.errortableDeviceExpriation;
            this.errinfo.errortableRepairStatus = datec.errortableRepairStatus;
            this.errinfo.errortableRequestTime = datec.errortableRequestTime;
            this.errinfo.errortableNote = datec.errortableNote;
            this.errinfo.errortableServercompany = datec.errortableServercompany;
            this.errinfo.errortableFaultclassification = datec.errortableFaultclassification;
            this.errinfo.errortableBelongPaltform = datec.errortableBelongPaltform;
            this.errinfo.errortableNewcreate = datec.errortableNewcreate;

            $('#updateinfoModal').modal('show');
        },

        upnewfile: function () {
            //校验文件是否是标准的XLSX格式
            var filepath = $("input[name='file']").val();
            var extStart = filepath.lastIndexOf(".");
            var ext = filepath.substring(extStart, filepath.length).toUpperCase();

            if (ext != ".XLSX") {
                console.log(ext)
                return this.uploadstate = "失败，请检查是否是上传的模板文件";
            } else {
                //校验完成后上传中

                this.uploadstate = "上传中，请不要操作，完成后该对话框自动关闭！";
                let formData = new FormData();
                //file对应传过去的参数
                formData.append('file', $('#fileunewpload')[0].files[0]);
                axios({
                    method: 'post',
                    url: "/daywork/uploadnewerrortable",
                    data: formData,
                    headers: {
                        //文件头必须写
                        'Content-Type': 'multipart/form-data'
                    }
                })
                    .then(res => {
                        console.log(res.data);
                        this.uploadstateclass = true;
                        this.uploadstate = "已经将以下文件上传成功";
                        $('#uploadCheck').modal('hide');
                    })
                    .catch(err => {
                        this.uploadstate = "!!上传失败,请检查文件是否正确!!";
                    });
            }
        },

        upallexporttable: function () {
            //校验文件是否是标准的XLSX格式
            var filepath = $("input[name='zxltjfile']").val();
            var extStart = filepath.lastIndexOf(".");
            var ext = filepath.substring(extStart, filepath.length).toUpperCase();

            if (ext != ".XLS") {
                console.log(ext)
                alert("失败，请检查是否是上传的模板文件");
            } else {
                //校验完成后上传中
                console.log("校验通过，正在处理");
                let formData = new FormData();
                //file对应传过去的参数
                formData.append('file', $('#zxltjfile')[0].files[0]);
                axios({
                    method: 'post',
                    url: "/daywork/uploadpmalltable",
                    data: formData,
                    headers: {
                        //文件头必须写
                        'Content-Type': 'multipart/form-data'
                    },
                    responseType: 'blob'
                })
                    .then(res => {
                        console.log(res.data);

                        if (!res.data) {
                            return
                        }
                        let url = window.URL.createObjectURL(new Blob([res.data]))
                        let link = document.createElement('a')
                        link.style.display = 'none'
                        link.href = url
                        link.setAttribute('download', 'excel.xlsx')

                        document.body.appendChild(link)
                        link.click()

                    })
                    .catch(e => {
						console.log(e)
                        alert("!!上传失败,请检查文件是否正确!!");
                    });
            }
        },


        // 根据条件查找到全局搜索
        search: function () {


            let formData = new FormData();
            //file对应传过去的参数
            formData.append('county', this.county);
            formData.append('dwname', this.dwname);
            formData.append('hkname', this.hkname);
            formData.append('sbip', this.sbip);
            formData.append('wbdw', this.wbdw);
            formData.append('gzfl', this.gzfl);
            formData.append('fxpt', this.fxpt);
            formData.append('sfxz', this.sfxz);


            axios({
                method: 'post',
                url: "/daywork/allsearch",
                data: formData,
                headers: {
                    //文件头必须写，这是网址头
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
                .then(res => {
                    console.log(res.data);
                    this.datecontent = res.data;
                })
                .catch(err => {
                    console.log("失败");
                });
        },

        //下面继续写方法
        //检查在数据库中IP的通断并且删除通了的数据
        checkanddelete: function () {

            let formData = new FormData();
            //file对应传过去的参数
            formData.append('ips', $("#jsonips").val());

            axios({
                method: 'post',
                url: "/daywork/checkanddelete",
                data: formData,
                headers: {
                    //文件头必须写，这是网址头
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
                .then(res => {
                    $('#IPmyModal').modal('hide');
                    this.pmcheckanddeleteclass = true;

                    if (!res.data) {
                        return
                    }
                    let url = window.URL.createObjectURL(new Blob([res.data]))
                    let link = document.createElement('a')
                    link.style.display = 'none'
                    link.href = url
                    link.setAttribute('download', 'repairedexcel.xlsx')

                    document.body.appendChild(link)
                    link.click()

                })
                .catch(err => {

                });
        },


        //下面继续写方法 显示展示下午的公司全部下载的列表模态框
        choosecompanymodel: function () {
            $('#choosecompanymodel').modal('show');
        },


        getpmtablebycompany: function (companynames, ifinpro, reason) {
            window.location.href = "/daywork/downloadfilemodel?companyname=" + companynames + "&ifinpro=" + ifinpro +
                "&reason=" + reason;

        },

        getpmtablebyerrortype: function (reason) {
            window.location.href = "/daywork/downloaderrtypemodel?reason=" + reason;
        },

        getpmtablebyifinpro: function (companyclass) {
            window.location.href = "/daywork/downloadfilemodelifinpro?companyclass=" + companyclass;
        },


        getmaintalbe: function () {
            window.location.href = "/daywork/downloadmaintable";
        },


        getpmtablebycounty: function (county) {
            window.location.href = "/daywork/downloadfilemodelcounty?county=" + county;

        },

        //下面继续写方法 上传以filebasepload为id的文件框，到接口中
        upnewfilebase: function () {

            //校验文件是否是标准的XLSX格式
            var filepath = $("input[name='basefile']").val();
            var extStart = filepath.lastIndexOf(".");
            var ext = filepath.substring(extStart, filepath.length).toUpperCase();

            if (ext != ".XLSX") {
                console.log(ext)
                return this.uploadstate = "失败，请检查是否是上传的模板文件";
            } else {
                //

                this.uploadstate = "上传中，请不要操作，完成后该对话框自动关闭！";


                let formData = new FormData();
                //file对应传过去的参数
                formData.append('basefile', $('#filebasepload')[0].files[0]);
                axios({
                    method: 'post',
                    url: "/daywork/uploadbasetable",
                    data: formData,
                    headers: {
                        //文件头必须写
                        'Content-Type': 'multipart/form-data'
                    }
                })
                    .then(res => {
                        console.log(res.data);
                        this.uploadstateclass = true;
                        this.uploadstate = "已经将以下文件上传成功";
                        $('#upfilebasev').modal('hide');


                    })
                    .catch(err => {
                        this.uploadstate = "!!上传失败,请检查文件是否正确!!";
                    });
            }
        }
    }
})
