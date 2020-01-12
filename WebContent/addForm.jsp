<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- form 태그는 uri 제작자이다. action에 적힌 목적지로 name을 키로 하여 파라미터를 만들어준다. -->
	<!-- 아래 form은 최종적으로 doAdd.jsp?title=??&body=??&nick=?? (여기서 ??는 사용자가 텍스트창에 입력한 값이다.) -->
	<form action="add.do">
		<input type="text" name="title" placeHolder="제목을 입력해주세요."/>
		<input type="text" name="body" placeHolder="내용을 입력해주세요."/>
		<input type="text" name="nick" placeHolder="작성자를 입력해주세요."/>
		<input type="submit" value="보내기" />
	</form>

</body>
</html>