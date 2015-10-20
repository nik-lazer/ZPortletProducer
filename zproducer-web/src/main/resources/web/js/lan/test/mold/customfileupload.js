function (out) {
	var multiple_ = (this.multiple && this.multiple == "true") ? " multiple" : "";
	var url_ = this.url.length > 0 ? this.url : "upload";

	out.push('<form id="fileuploadform" method="POST" enctype="multipart/form-data">');

    if (this.showDropzone == "true") {
        out.push('<div id="dropzone" class="fade well">Place file here for upload</div>');
    }
    out.push('<div class="btnHolder">');
    if (this.showDropzone == "true") {
        out.push('<div id="orSpan">or press</div>');
    }
	out.push('<div class="fileUpload btn">');
	out.push('    <span>Upload</span>');
	//out.push('<input id="', this.id, '" type="file" data-url="' , url_ ,'" name="files[]" ' ,
	out.push('<input id="', this.id, '" type="file" name="files[]" ',
			' class="upload ', this.getZclass(), '" ', multiple_, ' />');
	out.push('</div>');
	out.push('</div>');
	out.push('    <div id="error" class="alert alert-danger" style="display:none;"/>');
	if (this.showProgress == "true") {
		out.push('<div id="progress" class="progress">');
		out.push('<div class="bar" style="width: 0%;"></div>');
		out.push('</div>');
	}
	if (this.showFileList == "true") {
        if(this.maxNumberOfFiles == 1){
        } else {
           out.push('<b>Files:</b>');
        }
        out.push('<div class="tableWrapper">');
		out.push('<table id="uploaded-files" class="table">');
		out.push('</table>');
		out.push('</div>');
	}
	out.push('</form>');
}