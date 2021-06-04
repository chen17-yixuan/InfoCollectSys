var vm = new Vue({

	el: '#vue_det',

	data: {
		site: "等待",
		uploadstate: "等待上传",
		uploadstateclass: false,
		pmcheckanddeleteclass: false,
		pmcheckanddelete: "等待操作",
		datecontent: null,
		hkxjdatacont: null,
		hkxjcountys: null,
		hkxjsearchcounty: null,
		hkxjerrclass: null,
		errpointnote: null,
		county: null,
		dwname: null,
		hkname: null,
		sbip: null,
		wbdw: null,
		gzfl: null,
		fxpt: null,
		sfxz: null,
		mode: null,
		checkedall: "checked",
		checkedids: [],
		errortableRequestTimeShow: null,
		errortableRequestTimeCache: null,
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
			errortableShow: null,
			errortableRequestTime: null,
			errortableNote: null,
			errortableServercompany: null,
			errortableFaultclassification: null,
			errortableBelongPaltform: null,
			errortableNewcreate: null,
		},
		hkdevinfo: {
			pointid: null,
			pointname: "点击下一步开始任务",
			pointerrmainreason: null,
			pointerrsubreason: null,
			pointnote: null
		},

		hkdeverrclassifier: {
			normal: ["正常"],
			offline: ["不在线", "海康平台无法取流"],
			delay: ["卡顿", "海康平台无图像"],
			record: ["视频花屏", "视频白屏", "视频暗", "无录像回放", "录像回放查询失败"],
			cover: ["树叶遮挡", "路牌遮挡", "红绿灯遮挡", "灰尘多"],
			mark: ["字符叠加缺失", "字符叠加位置", "路口名称与监控点名称不一致", "日期格式", "日期位置"]
		}

	},

	beforeMount() {
		axios({
				method: 'post',
				url: "/hkabout/initRefresh",
				headers: {
					//文件头必须写
					'Content-Type': 'multipart/form-data'
				}
			})
			.then(res => {
				console.log(res.data);
				this.$set(this, 'hkxjcountys', res.data);
			})
	},

	methods: {
		deletebyids: function(singleid) {
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

		selectChecked: function(datecid) {

			var pos = this.checkedids.indexOf(datecid);
			if (pos != -1) {
				this.checkedids.splice(pos, 1)
			} else {
				this.checkedids.push(datecid);
			}


		},

		updateinfoModalByGroup: function() {
			$('#updateinfoModalByGroup').modal('show');
		},

		insertinfoModal: function() {
			$('#insertinfoModal').modal('show');
		},

		updateinfoModalByGroupConfirm: function() {
			if (confirm('确定修改？')) {
				let formData = new FormData();
				//file对应传过去的参数
				formData.append('ids', JSON.stringify(this.checkedids));
				formData.append('updatedata', JSON.stringify(this.errinfo));

				axios({
						method: 'post',
						url: "/daywork/updateByIds",
						data: formData,
						headers: {
							//文件头必须写，这是网址头
							'Content-Type': 'application/x-www-form-urlencoded'
						}
					})
					.then(res => {
						if (res.data == 'fail1') {
							alert("修改失败，请保证必填质保状态、维保单位、故障分类，并重试");
						} else {
							this.search();
							$('#updateinfoModalByGroup').modal('hide');
							this.errinfo.errortableServercompany = null;
							this.errinfo.errortableFaultclassification = null;
							this.errinfo.errortableDeviceExpriation = null;
							this.errinfo.errortableNote = null;
							this.errinfo.errortableRepairStatus = null;
							this.checkedids = [];
						}
					})
					.catch(err => {
						alert("修改失败")
					});
			}
		},

		updatebyid: function() {

			let formData = new FormData();
			//file对应传过去的参数
			formData.append('updatedata', JSON.stringify(this.errinfo));
			console.log(this.errinfo)
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

		insertNewRec: function() {
			if (this.errinfo.errortableCounty == null) {
				alert("区县不能为空")
				return;
			}
			if (this.errinfo.errortableDianweiname == null) {
				alert("点位名称不能为空")
				return;
			}
			if (this.errinfo.errortableDeviceIp == null) {
				alert("设备IP不能为空")
				return;
			}
			if (this.errinfo.errortableDeviceExpriation == null) {
				alert("质保情况不能为空")
				return;
			}
			if (this.errinfo.errortableServercompany == null) {
				alert("服务公司不能为空")
				return;
			}
			if (this.errinfo.errortableFaultclassification == null) {
				alert("故障分类:不能为空")
				return;
			}


			let formData = new FormData();
			//file对应传过去的参数
			formData.append('insertdata', JSON.stringify(this.errinfo));
			console.log(this.errinfo)
			axios({
					method: 'post',
					url: "/daywork/insertNewRec",
					data: formData,
					headers: {
						//文件头必须写，这是网址头
						'Content-Type': 'application/x-www-form-urlencoded'
					}
				})
				.then(res => {
					alert("上传成功")
					$('#insertinfoModal').modal('hide');
					location.reload()
				})
				.catch(err => {
					alert("上传失败，请检查")
				});

		},

		updateinfoModal: function(datec) {
			this.errinfo.errortableId = datec.errortableId;
			this.errinfo.errortableCounty = datec.errortableCounty;
			this.errinfo.errortableDianweiname = datec.errortableDianweiname;
			this.errinfo.errortableHaikangpname = datec.errortableHaikangpname;
			this.errinfo.errortableHaixinpname = datec.errortableHaixinpname;
			this.errinfo.errortableDeviceType = datec.errortableDeviceType;
			this.errinfo.errortableDeviceIp = datec.errortableDeviceIp;
			this.errinfo.errortableBuildCompany = datec.errortableBuildCompany;
			this.errinfo.errortableDeviceExpriation = datec.errortableDeviceExpriation;
			this.errinfo.errortableShow = datec.errortableFaultclassification;
			this.errinfo.errortableRepairStatus = datec.errortableRepairStatus;
			this.errortableRequestTimeShow = this.dateFilter(datec.errortableRequestTime);
			this.errinfo.errortableRequestTime = datec.errortableRequestTime;
			this.errinfo.errortableNote = datec.errortableNote;
			this.errinfo.errortableServercompany = datec.errortableServercompany;
			this.errinfo.errortableFaultclassification = datec.errortableFaultclassification;
			this.errinfo.errortableBelongPaltform = datec.errortableBelongPaltform;
			this.errinfo.errortableNewcreate = datec.errortableNewcreate;

			$('#updateinfoModal').modal('show');
		},


		dateFilter: function(input) {
			var d = new Date(input);
			var year = d.getFullYear();
			var month = d.getMonth() < 9 ? "0" + (d.getMonth() + 1) : "" + (d.getMonth() + 1);
			var day = d.getDate() < 10 ? "0" + d.getDate() : "" + d.getDate();
			var hour = d.getHours() < 10 ? "0" + d.getHours() : "" + d.getHours();
			var minutes = d.getMinutes() < 10 ? "0" + d.getMinutes() : "" + d.getMinutes();
			var seconds = d.getSeconds() < 10 ? "0" + d.getSeconds() : "" + d.getSeconds();
			return (year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds);
		},

		upnewfile: function() {
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

		showuploadHKAllDev: function() {
			$('#uploadHKAllDev').modal('show');
		},

		upnewfileHKAllDev: function() {
			//校验文件是否是标准的XLSX格式
			var filepath = $("input[name='fileHKAllDev']").val();
			var extStart = filepath.lastIndexOf(".");
			var ext = filepath.substring(extStart, filepath.length).toUpperCase();

			if (ext != ".XLS") {
				console.log(ext)
				return this.uploadstate = "失败，请检查是否是上传的模板文件";
			} else {
				//校验完成后上传中

				this.uploadstate = "上传中，请不要操作，完成后该对话框自动关闭！";
				let formData = new FormData();
				//file对应传过去的参数
				formData.append('file', $('#fileunewploadHKAllDev')[0].files[0]);
				axios({
						method: 'post',
						url: "/hkabout/uploadHKallDev",
						data: formData,
						headers: {
							//文件头必须写
							'Content-Type': 'multipart/form-data'
						}
					})
					.then(res => {
						console.log(res.data);
						$('#uploadHKAllDev').modal('hide');
						this.refreashHkCounty();
					})
					.catch(err => {
						this.uploadstate = "!!上传失败,请检查文件是否正确!!";
					});
			}
		},

		upallexporttable: function() {
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
		search: function() {


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
		checkanddelete: function() {

			let formData = new FormData();
			//file对应传过去的参数
			formData.append('ips', $("#jsonips").val());

			axios({
					method: 'post',
					url: "/daywork/checkanddelete",
					data: formData,
					responseType: 'blob',
					headers: {
						//文件头必须写，这是网址头
						'Content-Type': 'application/x-www-form-urlencoded'
					}
				})
				.then(res => {
					$('#IPmyModal3').modal('hide');
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
		},


		//下面继续写方法 显示展示下午的公司全部下载的列表模态框
		choosecompanymodel: function() {
			$('#choosecompanymodel').modal('show');
		},


		getpmtablebycompany: function(companynames, ifinpro, reason) {
			window.location.href = "/daywork/downloadfilemodel?companyname=" + companynames + "&ifinpro=" + ifinpro +
				"&reason=" + reason;
		},

		getpmtablebyerrortype: function(reason) {
			window.location.href = "/daywork/downloaderrtypemodel?reason=" + reason;
		},

		getpmtablebyifinpro: function(companyclass) {
			window.location.href = "/daywork/downloadfilemodelifinpro?companyclass=" + companyclass;
		},


		getmaintalbe: function() {
			window.location.href = "/daywork/downloadmaintable";
		},


		getpmtablebycounty: function(county) {
			window.location.href = "/daywork/downloadfilemodelcounty?county=" + county;

		},

		getSingleCountyRecord: function(orgname) {
			this.errpointnote = "";
			this.hkxjsearchcounty = orgname;
			$('#IPmyModalhkxj').modal('show');
			console.log(this.hkxjsearchcounty);
			var Url2 = document.getElementById("jkdname");
			Url2.value = "点击下一步开始任务";
			this.hkdevinfo.pointid = null;
			this.hkdevinfo.pointname = "点击下一步开始任务";
			this.hkdevinfo.pointerrmainreason = null;
			this.hkdevinfo.pointerrsubreason = null;
			if (this.hkxjsearchcounty == "err") {
				this.mode = this.hkxjsearchcounty;
			} else {
				this.mode = "norOrg";
			}
			console.log(this.hkxjsearchcounty)
		},

		getNextSingleCountyRecord: function() {
			this.errpointnote = "";
			if (this.mode != "err") {
				if (this.hkdevinfo.pointerrsubreason == null || this.hkdevinfo.pointerrmainreason == null) {
					alert("请注明主原因和详细原因");
				} else {
					this.errpointnote = "";
					let formData = new FormData();
					//file对应传过去的参数
					formData.append('pointid', this.hkdevinfo.pointid);
					formData.append('pointname', this.hkdevinfo.pointname);
					formData.append('pointerrmainreason', this.hkdevinfo.pointerrmainreason);
					formData.append('pointerrsubreason', this.hkdevinfo.pointerrsubreason);
					formData.append('pointnote', this.hkdevinfo.pointnote);
					formData.append('pointorg', this.hkxjsearchcounty);

					axios({
							method: 'post',
							url: "/hkabout/getSingleRecord",
							data: formData,
							headers: {
								//文件头必须写，这是网址头
								'Content-Type': 'application/x-www-form-urlencoded'
							}
						})
						.then(res => {
							console.log(res.data.deviceSn);
							this.hkdevinfo.pointid = res.data.deviceSn;
							this.hkdevinfo.pointname = res.data.deviceName;
							this.hkdevinfo.pointerrsubreason = null;
							this.hkdevinfo.pointnote = null;
							var Url2 = document.getElementById("jkdname");
							Url2.value = res.data.deviceName;
							Url2.select();
							document.execCommand("Copy");
						})
						.catch(err => {
							alert("该地区已巡检完成");
							this.refreashHkCounty();
							$('#IPmyModalhkxj').modal('hide');
						});
				}
			} else {
				let formData = new FormData();
				//file对应传过去的参数
				formData.append('pointid', this.hkdevinfo.pointid);
				formData.append('pointname', this.hkdevinfo.pointname);
				formData.append('pointerrmainreason', this.hkdevinfo.pointerrmainreason);
				formData.append('pointerrsubreason', this.hkdevinfo.pointerrsubreason);
				formData.append('pointnote', this.hkdevinfo.pointnote);

				axios({
						method: 'post',
						url: "/hkabout/getSingleErrRecord",
						data: formData,
						headers: {
							//文件头必须写，这是网址头
							'Content-Type': 'application/x-www-form-urlencoded'
						}
					})
					.then(res => {
						console.log(res.data);
						this.hkdevinfo.pointid = res.data.deviceSn;
						this.hkdevinfo.pointname = res.data.incidentDevname;
						this.hkdevinfo.pointerrmainreason = res.data.incidentProblem;
						this.hkdevinfo.pointerrsubreason = res.data.incidentSubproblem;
						this.errpointnote = "（" + res.data.incidentProblem + "-" + res.data.incidentSubproblem + "）" ;
						this.hkdevinfo.pointnote = res.data.incidentNote;
						var Url2 = document.getElementById("jkdname");
						Url2.value = res.data.incidentDevname;
						Url2.select();
						document.execCommand("Copy");
					})
					.catch(err => {
						alert("该地区已巡检完成");
						this.refreashHkCounty();
						$('#IPmyModalhkxj').modal('hide');
					});
			}

		},

		doex: function(classname) {

			if (classname == "normal") {
				this.$set(this, 'hkxjerrclass', this.hkdeverrclassifier.normal);
				this.hkdevinfo.pointerrmainreason = "正常";
			};
			if (classname == "offline") {
				this.$set(this, 'hkxjerrclass', this.hkdeverrclassifier.offline);
				this.hkdevinfo.pointerrmainreason = "不在线";
			};
			if (classname == "delay") {
				this.$set(this, 'hkxjerrclass', this.hkdeverrclassifier.delay);
				this.hkdevinfo.pointerrmainreason = "卡顿";
			};
			if (classname == "record") {
				this.$set(this, 'hkxjerrclass', this.hkdeverrclassifier.record);
				this.hkdevinfo.pointerrmainreason = "录像问题";
			};
			if (classname == "cover") {
				this.$set(this, 'hkxjerrclass', this.hkdeverrclassifier.cover);
				this.hkdevinfo.pointerrmainreason = "异物遮挡";
			};
			if (classname == "mark") {
				this.$set(this, 'hkxjerrclass', this.hkdeverrclassifier.mark);
				this.hkdevinfo.pointerrmainreason = "标注错误";
			};
			this.hkdevinfo.pointerrsubreason = null;
			console.log(this.hkdevinfo.pointerrmainreason);
		},

		refreashHkCounty: function() {
			axios({
					method: 'post',
					url: "/hkabout/initRefresh",
					headers: {
						//文件头必须写
						'Content-Type': 'multipart/form-data'
					}
				})
				.then(res => {
					console.log(res.data);
					this.$set(this, 'hkxjcountys', res.data);
				})
		},

		//下面继续写方法 上传以filebasepload为id的文件框，到接口中
		upnewfilebase: function() {

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
