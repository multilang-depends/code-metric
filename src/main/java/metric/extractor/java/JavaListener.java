/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package metric.extractor.java;

import depends.extractor.java.JavaParser;
import depends.extractor.java.JavaParserBaseListener;

public class JavaListener extends JavaParserBaseListener {


	private MetricContext context;

	public JavaListener(String fileFullPath, MetricContext context) {
		this.context = context;
	}

	@Override
	public void enterPackageDeclaration(JavaParser.PackageDeclarationContext ctx) {
		super.enterPackageDeclaration(ctx);
		context.addStmt();
	}

	@Override
	public void enterImportDeclaration(JavaParser.ImportDeclarationContext ctx) {
		super.enterImportDeclaration(ctx);
		context.addStmt();
	}

	@Override
	public void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx) {
		super.enterFieldDeclaration(ctx);
		context.addStmt();
	}

	@Override
	public void enterAnnotationTypeElementRest(JavaParser.AnnotationTypeElementRestContext ctx) {
		super.enterAnnotationTypeElementRest(ctx);
		context.addStmt();
	}

	@Override
	public void enterConstDeclaration(JavaParser.ConstDeclarationContext ctx) {
		super.enterConstDeclaration(ctx);
		context.addStmt();
	}

	@Override
	public void enterLocalVariableDeclaration(JavaParser.LocalVariableDeclarationContext ctx) {
		super.enterLocalVariableDeclaration(ctx);
		context.addStmt();
	}

	@Override
	public void enterStatement(JavaParser.StatementContext ctx) {
		super.enterStatement(ctx);
		context.addStmt();
	}

	@Override
	public void enterResourceSpecification(JavaParser.ResourceSpecificationContext ctx) {
		super.enterResourceSpecification(ctx);
		context.addStmt();
	}

	@Override
	public void enterBlock(JavaParser.BlockContext ctx) {
		super.enterBlock(ctx);
		context.addStmt();
	}
}
