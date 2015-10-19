<%@ page contentType="text/css; charset=UTF-8" %>
<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %>
<%@ taglib uri="http://www.zkoss.org/dsp/zk/core" prefix="z" %>
${z:setCSSCacheControl()}

.progress {
	height: 15px;
	margin-bottom: 15px;
	overflow: hidden;
	border: solid 1px #CBCBCC;
	margin-top: 10px;
}

.progress .bar {
	float: left;
	width: 0;
	height: 100%;
	font-size: 12px;
	color: #ffffff;
	text-align: center;
	text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
	background-color: #2C8BB9;
}

.progress .bar + .bar {
	-webkit-box-shadow: inset 1px 0 0 rgba(0, 0, 0, 0.15), inset 0 -1px 0 rgba(0, 0, 0, 0.15);
	-moz-box-shadow: inset 1px 0 0 rgba(0, 0, 0, 0.15), inset 0 -1px 0 rgba(0, 0, 0, 0.15);
	box-shadow: inset 1px 0 0 rgba(0, 0, 0, 0.15), inset 0 -1px 0 rgba(0, 0, 0, 0.15);
}

.btnHolder{
  	display: table-cell;
  	font-family: Arial;
    font-size: 12px;
    padding-left: 10px;
}

.btn {
	background-image:url(${c:encodeThemeURL('~./icons/24/apply_changes.png')});
	background-position: -5px 0;
	background-repeat: no-repeat;
	display: inline-block;
	*display: inline;
	margin-bottom: 0;
	*margin-left: .3em;
	font-size: 12px;
	color: #1667A7;
	padding-left: 20px;
	line-height: 20px;
	text-align: center;
	cursor: pointer;
}

.btn:hover, .btn:focus{
	background-position: -5px -25px;
}

.btn:active,
.btn.active {
	background-position: -5px -76px;
}

.btn.disabled,
.btn[disabled] {
    background-position: -5px -50px;
}

.btn:first-child {
	*margin-left: 0;
}

.fileUpload {
	position: relative;
	overflow: hidden;
	margin: 10px 0px;
}

.fileUpload input.upload {
	position: absolute;
	top: 0;
	right: 0;
	margin: 0;
	padding: 0;
	font-size: 20px;
	cursor: pointer;
	opacity: 0;
	filter: alpha(opacity=0);
}

.alert {
	padding: 8px 35px 8px 14px;
	margin-bottom: 20px;
	margin-top: 10px;
	text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
	background-color: #fcf8e3;
	border: 1px solid #fbeed5;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
}

.alert {
	color: #c09853;
}

.alert-danger {
	color: #b94a48;
	background-color: #f2dede;
	border-color: #eed3d7;
	font-size: 10px;
}

.alert-danger h4 {
	color: #b94a48;
}

.table {
	width: 100%;
	font-family: "Arial", sans-serif;
	font-size: 12px;
	font-weight: normal;

}

table {
	max-width: 100%;
	background-color: transparent;
	border-collapse: collapse;
	border-spacing: 0px;
	font-family: "Arial", sans-serif;
	font-size: 12px;
	font-weight: normal;
}

.table th, .table td {
	padding-bottom: 5px;
	text-align: left;
	vertical-align: top;
}

.table .name {
	overflow: hidden;
}

.table .filesize, .table .sparator {
	color: #7f7f7f;
	padding-left: 15px;
	display: inline-block;
}

.table .error {
	color: #b94a48;
}

.table .button {
	text-align: right;
	display: inline-block;
}

.button a, .button a:hover, .button a:focus, .button a:active {
	background-image:url(${c:encodeThemeURL('~./icons/16/wnd-exit.png')});
    background-position: 0 0;
    background-repeat: no-repeat;
    padding-left: 20px;
	color: #0088cc;
	text-decoration: none;
	cursor: pointer;
	display: inline-block;
	height: 17px;
}

.button a:hover,
.button a:focus {
	color: #005580;
	text-decoration: underline;
	background-position: 0 -25px;
}

.button a:active {
	background-position: 0 -75px;
}

.tableWrapper{
   overflow: auto;
   max-height: 150px;
   display: block;
   border-bottom: 1px solid #c2c2c2;
   margin-top: 10px;
}

#dropzone {
    background: #EEEEEF;
    display: table-cell;
    height: 20px;
    margin-right: 10px;
    line-height: 20px;
    text-align: center;
    color: #A9AAAD;
    padding: 25px;
    vertical-align: middle;
    font-family: Arial;
    font-size: 12px;
    width: 260px;
}

#orSpan{
	padding-top: 10px;
}

#fileuploadform{
	font-family: Arial;
    font-size: 12px;
}