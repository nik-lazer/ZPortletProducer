lan.test.CustomFileUpload = zk.$extends(zul.Widget, {

	url: '',
	multiple: "",
	maxFileSize: 0,
	maxNumberOfFiles: 0,
	showProgress: "",
	showFileList: "",
	totalCount: 0,
	extensionsSet: '',
	isAllowedList: 'true',
	showDropzone: 'true',

	getMultiple: function () {
		return this.multiple;
	},

	setMultiple: function (multiple) {
		if (this.multiple != multiple) {
			this.multiple = multiple;
			if (multiple == "false") {
				if (this.desktop) {
					$('#' + this.id).removeAttr('multiple');
				} else {
					$('#' + this.id).attr('multiple', '');
				}
			}
		}
	},

	getUrl: function () {
		return this.url;
	},

	setUrl: function (url) {
		if (this.url != url) {
			this.url = url;
			//if (this.desktop) this.$n().innerHTML = zUtl.encodeXML(value);
		}
	},

	getMaxFileSize: function () {
		return this.maxFileSize;
	},

	setMaxFileSize: function (maxFileSize) {
		if (this.maxFileSize != maxFileSize) {
			this.maxFileSize = maxFileSize;
			//if (this.desktop) this.$n().innerHTML = zUtl.encodeXML(value);
		}
	},

	getMaxNumberOfFiles: function () {
		return this.maxNumberOfFiles;
	},

	setMaxNumberOfFiles: function (maxNumberOfFiles) {
		if (this.maxNumberOfFiles != maxNumberOfFiles) {
			this.maxNumberOfFiles = maxNumberOfFiles;
			//if (this.desktop) this.$n().innerHTML = zUtl.encodeXML(value);
		}
	},

	getShowProgress: function () {
		return this.showProgress;
	},

	setShowProgress: function (showProgress) {
		if (this.showProgress != showProgress) {
			this.showProgress = showProgress;
			//if (this.desktop) this.$n().innerHTML = zUtl.encodeXML(value);
		}
	},

	getShowFileList: function () {
		return this.showFileList;
	},

	setShowFileList: function (showFileList) {
		if (this.showFileList != showFileList) {
			this.showFileList = showFileList;
			//if (this.desktop) this.$n().innerHTML = zUtl.encodeXML(value);
		}
	},

	getShowDropzone: function () {
    	return this.showDropzone;
    },

    setShowDropzone: function (showDropzone) {
    	if (this.showDropzone != showDropzone) {
    		this.showDropzone = showDropzone;
    	}
    },

	getTotalCount: function () {
		return this.totalCount;
	},

	setTotalCount: function (totalCount) {
		if (this.totalCount != totalCount) {
			this.totalCount = totalCount;
			//if (this.desktop) this.$n().innerHTML = zUtl.encodeXML(value);
		}
	},

	humanFileSize: function (bytes, si) {
		var thresh = si ? 1000 : 1024;
		if (bytes < thresh) return bytes + ' B';
		var units = si ? ['kB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'] : ['KiB', 'MiB', 'GiB', 'TiB', 'PiB', 'EiB', 'ZiB', 'YiB'];
		var u = -1;
		do {
			bytes /= thresh;
			++u;
		} while (bytes >= thresh);
		return bytes.toFixed(1) + ' ' + units[u];
	},

	hideErrors: function () {
		$('#error').css('display', 'none').text('');
	},

	showErrors: function (uploadErrors) {
		var err = $('#error').css('display', 'block').text('');
		$.each(uploadErrors, function (index, error) {
			err.append(error + "<br/>")
		});
	},

	define: {
	},

	bind_: function (evt) {
		try {
			this.$supers('bind_', arguments);

			var wgt = this;
			var dtid_ = this.desktop.id;

			$('#fileupload').fileupload({

				dataType: 'json',
				formAcceptCharset: 'utf-8',
				sequentialUploads: true,
				url: wgt.url,

				add: function (e, data) {
					var uploadErrors = [];
					if (wgt.maxFileSize && wgt.maxFileSize > 0) {
						$.each(data.originalFiles, function (index, file) {
							if (file.size && file.size > wgt.maxFileSize) {
								uploadErrors.push('Слишком большой файл: "' + file.name + '"');
							}
						});
					}
					if (wgt.isAllowedList == 'true' && wgt.extensionsSet && wgt.extensionsSet != '') {
						$.each(data.originalFiles, function (index, file) {
							var extensionsArray = wgt.extensionsSet.split(",");
							var notAccepted = 1;
							for (var i = 0; i < extensionsArray.length; i++) {
								if (file.name.toLowerCase().endsWith(extensionsArray[i])) {
									notAccepted = 0;
									break
								}
							}
							if (notAccepted == 1) {
								uploadErrors.push('Вложения данного типа не разрешены. Допустимы расширения: "' + wgt.extensionsSet + '"');
							}
						});
					} else if (wgt.extensionsSet && wgt.extensionsSet != '') {
						$.each(data.originalFiles, function (index, file) {
							var extensionsArray = wgt.extensionsSet.split(",");
							var notAccepted = 0;
							for (var i = 0; i < extensionsArray.length; i++) {
								if (file.name.toLowerCase().endsWith(extensionsArray[i])) {
									notAccepted = 1;
									break
								}
							}
							if (notAccepted == 1) {
								uploadErrors.push('Вложения данного типа не разрешены. Запрещенные расширения: "' + wgt.extensionsSet + '"');
							}
						});
					}
					if (wgt.maxNumberOfFiles && wgt.maxNumberOfFiles > 0) {
						var totalUploaded = $("#uploaded-files .button").length;
						if (wgt.showFileList != "true") {
							totalUploaded = wgt.totalCount;
						}
						// Если задано ограничение в 1 файл - заменяем, не кидаем ошибку
						if (totalUploaded == 1 && wgt.maxNumberOfFiles == 1) {
							zAu.send(new zk.Event(wgt, 'onFilesClear', '', {
								toServer: true
							}));
							$("#uploaded-files").empty();
						}
						else if (wgt.maxNumberOfFiles <= totalUploaded) {
							uploadErrors.push('Превышено максимальное количество загружаемых файлов');
						}
					}
					if (uploadErrors.length == 0) {
						data.submit();
						wgt.hideErrors();
						zAu.send(new zk.Event(wgt, 'onFileAdd', data.originalFiles, {
							toServer: true
						}));
					}
					else {
						wgt.showErrors(uploadErrors);
					}
				},

				progressall: function (e, data) {
					if (wgt.showProgress == "true") {
						var progress = parseInt(data.loaded / data.total * 100, 10);
						$('#progress .bar').css(
								'width',
								progress + '%'
						);
					}
				},

			});

			$('#fileupload').fileupload({
				dropZone: $('#dropzone')
			});

			$('#fileupload').bind('fileuploaddone', function (e, data) {
				var uploadErrors = [];
				var filesToUpload = [];
				$.each(data.result.files, function (index, file) {
					var totalUploaded = $("#uploaded-files .button").length;
					if (wgt.showFileList != "true") {
						totalUploaded = wgt.totalCount;
					}
					if (!wgt.maxNumberOfFiles || wgt.maxNumberOfFiles <= 0 || totalUploaded < wgt.maxNumberOfFiles) {
						var tr = $('<tr/>');
						var del = $('<a/>', {
							text: '',
							click: function () {
								tr.remove();
								wgt.hideErrors();
								zAu.send(new zk.Event(wgt, 'onFileDelete', file, {
									toServer: true
								}));
							}
						});
						var td = $('<td/>');
						tr.append(td);
						var fileName = wgt.maxNumberOfFiles == 1 ? "Файл: " + file.fileName : file.fileName;
						td.append($('<span/>').addClass("name").text(fileName)).append($('<span/>').addClass("sparator").text("|"));
						td.append($('<span/>').addClass("filesize").text(wgt.humanFileSize(file.fileSize, true)));
						if (file.error) {
							td.append($('<div/>').addClass("error").text(file.error));
						} else {
							td.append($('<div/>').addClass("button").append(del));
						}
						filesToUpload.push(file);
						if (wgt.showFileList == "true") {
							$("#uploaded-files").append(tr);
						}
					} else {
						uploadErrors.push('Превышено максимальное количество загружаемых файлов');
						wgt.showErrors(uploadErrors);
					}
				});
				if (wgt.showProgress == "true") {
					$('#progress .bar').css('width', '0%');
				}
				zAu.send(new zk.Event(wgt, 'onFileUpload', filesToUpload, {
					toServer: true
				}));
			})
					.bind('fileuploadsubmit', function (e, data) {
						data.formData = {
							dtid: dtid_,
							compId: wgt.uuid
						};
					});

		} catch(error) {
			console.log(error);
		}
	},


	getZclass: function () {
		var zcls = this._zclass;
		return zcls ? zcls : 'z-upload';
	}
});