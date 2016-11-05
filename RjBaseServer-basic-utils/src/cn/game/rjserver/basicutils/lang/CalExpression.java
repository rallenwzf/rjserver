package cn.game.rjserver.basicutils.lang;

import java.util.Stack;

/**
 * @author Westmoon 联系方式：Westmoon@yeah.net 设计思路： 转换过程包括用下面的算法读入中缀表达式的操作数、操作符和括号：
 *         初始化一个空堆栈，将结果字符串变量置空。 从左到右读入中缀表达式，每次一个字符。 如果字符是操作数，将它添加到结果字符串。
 *         如果字符是个操作符，弹出（pop）操作符，直至遇见开括号（opening
 *         parenthesis）、优先级较低的操作符或者同一优先级的右结合符号。把这个操作符压入（push）堆栈。
 *         如果字符是个开括号，把它压入堆栈。 如果字符是个闭括号（closing
 *         parenthesis），在遇见开括号前，弹出所有操作符，然后把它们添加到结果字符串。
 *         如果到达输入字符串的末尾，弹出所有操作符并添加到结果字符串。 后缀表达式求值
 *         对后缀表达式求值比直接对中缀表达式求值简单。在后缀表达式中，不需要括号，而且操作符的优先级也不再起作用了。您可以用如下算法对后缀表达式求值
 *         ： 初始化一个空堆栈 从左到右读入后缀表达式 如果字符是一个操作数，把它压入堆栈。
 *         如果字符是个操作符，弹出两个操作数，执行恰当操作，然后把结果压入堆栈。如果您不能够弹出两个操作数，后缀表达式的语法就不正确。
 *         到后缀表达式末尾，从堆栈中弹出结果。若后缀表达式格式正确，那么堆栈应该为空。
 */

public class CalExpression {

	public static void main(String[] args) {

		String string = "125/{v}+48*(1+2)";
		String str = string.replaceAll("\\{v\\}", "25");
		System.out.println(str);
		System.out.println((int) Double.parseDouble(eval(str)));
	}

	@SuppressWarnings("unchecked")
	private static String buildString(String s) {

		// 定义一个栈
		// 定义结果字符串res
		// 定义格式化字符串tmp
		// 定义格式化后字符串的长度len
		// 定义循环变量 c

		StringBuilder res = new StringBuilder();
		StringBuilder tmp = new StringBuilder();
		int len;
		char c;
		/*
		 * Format 字符串，只支持 + - * 和 / % ^ 等 以及 ( )等符号 多余的空格全部清空 如果还有其他字符（除了 数字 和
		 * 小数点 ），程序将返回出错信息。
		 */
		for (int i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			if (c >= '0' && c <= '9')
				tmp.append(c);
			else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')' || c == '.' || c == '%'
					|| c == '^') {
				tmp.append(c);
			} else if (c == ' ')
				;
			else {
				return "表达式含非法字符!";
			}
		}
		/*
		 * 将中缀表达式转换成后缀表达式
		 */
		@SuppressWarnings("rawtypes")
		Stack stk = new Stack();
		len = tmp.length();
		for (int i = 0; i < len; i++) {
			c = tmp.charAt(i);

			// 负号的处理

			if (i == 0 && (c < '0' || c > '9')) {
				if (c == '+' || c == '-') {
					res.append("#");
					if (c == '-')
						res.append('!');
					continue;
				}

				// 允许括号嵌套

				else if (c != '(' && c != '.') {
					return "表达式格式有误!";
				}
			}

			// 如果是数字或者是小数点，直接加到结果字符串中

			if ((c >= '0' && c <= '9') || c == '.') {
				res.append(c);
			}

			// 如果是符号，在结果中加入一个#号间隔
			// 然后进入判断如何操作

			else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')' || c == '%' || c == '^') {
				try {
					res.append("#");

					// 栈空，直接进栈

					if (stk.empty() == true) {
						stk.push(c);
					}

					// 栈顶是左括号 ，直接压栈 ，当前不是 ( 或者 )

					else if (stk.peek().toString().equals("(") && c != ')' && c != '(') {
						stk.push(c);
					}

					// 左括号，直接进栈

					else if (c == '(') {
						char cn = tmp.charAt(i + 1);
						if (cn < '0' || cn > '9') {

							// 将 （ 后的第一个 符号进行转义

							if (cn == '+' || cn == '-') {
								res.append("#");
								if (cn == '-')
									res.append('!');
								i++;
							}

							// 允许括号嵌套

							else if (c != '(' && c != '.') {
								return "表达式格式有误!";
							}
						}
						stk.push(c);
					}

					// 如果是右括号，一直弹出，直到有左括号为止，弹出的符号，全都添加到res串上

					else if (c == ')') {
						String str = stk.pop().toString();
						while (!str.equals("(")) {
							res.append(str);
							str = stk.pop().toString();
						}
					}

					// 如果当前只是普通的 运算符 ,栈顶也是普通的 运算符

					else {
						int ii = CalExpression.power(c);
						int jj = CalExpression.power(stk.peek().toString().charAt(0));

						// 同级或者高级符号，弹出，加到结果字符串中，前提是栈不能为空，并且栈顶不能是左括号

						while (ii <= jj && !stk.empty() && !stk.peek().toString().equals("(")) {
							res.append(stk.pop().toString());
							if (!stk.empty())
								jj = CalExpression.power(stk.peek().toString().charAt(0));
						}

						// 优先级低，货栈空，直接进栈

						if (ii > jj || stk.empty()) {
							stk.push(c);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		// 将站里面的所有内容弹出，返回到结果字符串中

		while (!stk.empty()) {
			res.append(stk.pop().toString());
		}
		return res.toString();
	}

	/*
	 * 计算后缀表达式,如果buildstring中产生异常，直接返回，否则计算
	 */
	public static String eval(String zz) {
		try {
			double l = Double.parseDouble(zz);
			return l + "";
		} catch (Exception e) {
		}
		Stack stk = new Stack();
		String hz = buildString(zz);
		if ((int) (hz.charAt(0)) > 1000)
			return hz;
		StringBuilder tmp = new StringBuilder();
		char c;
		int len = hz.length();
		try {
			for (int i = 0; i < len; i++) {
				c = hz.charAt(i);

				// 如果为数值，添加到缓冲串中

				if (c >= '0' && c <= '9') {
					tmp.append(c);
				}

				// 如果是小数点，添加到结果串中

				else if (c == '.') {
					tmp.append(c);
				}

				// 如果碰到间隔符 # ，检查缓冲串的内容和栈顶的内容是否同时是 - ，
				// 如果是，pop一个，如果不是将tmp压栈，并清空tmp

				else if (c == '#' && !tmp.toString().equals("")) {
					if (tmp.toString().equals("-")) {
						if (stk.peek().toString().equals("-")) {
							stk.pop();
						} else {
							stk.push(tmp);
						}
					} else {
						float f = Float.parseFloat(tmp.toString());
						stk.push(f);
					}
					tmp = new StringBuilder();
				}

				// 如果碰到！，在缓冲串后追加 -

				else if (c == '!') {
					tmp.append('-');
				} else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^') {

					// 如果tmp中有数据，直接压栈

					if (!tmp.toString().equals("")) {
						stk.push(tmp.toString());
					}

					// b是第二个操作数，while是检查b与前一个数之间，-的个数，从而确定 b 的符号

					float b = Float.parseFloat(stk.pop().toString());
					while (!stk.empty() && stk.peek().toString().equals("-")) {
						stk.pop();
						b = -b;
					}
					float a = Float.parseFloat(stk.pop().toString());
					switch (c) {
					case '+':
						stk.push(a + b);
						break;
					case '-':
						stk.push(a - b);
						break;
					case '*':
						stk.push(a * b);
						break;
					case '/':
						if (b == 0)
							return "除数不能为0！";
						stk.push(a / b);
						break;
					case '%':
						stk.push(a % b);
						break;
					case '^':
						stk.push(Math.pow(a, b));
						break;
					default:
					}
				}
			}
		} catch (Exception e) {
			return "表达式无法计算！";
		}
		return stk.pop().toString();
	}

	// 计算运算符的优先级

	private static int power(char c) {
		int n = 0;
		switch (c) {
		case '+':
		case '-':
			n = 0;
			break;
		case '*':
		case '/':
		case '%':
		case '^':
			n = 1;
			break;
		case '(':
		case ')':
			n = -1;
			break;
		default:
		}
		return n;
	}
}
