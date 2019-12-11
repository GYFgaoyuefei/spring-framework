package com.eseasky.core.framework.AuthService.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BinOrListUtil {
	public static List<String> transToBin(int action) {
		List<String> actions = new ArrayList<String>();
		;
		actions.add(action / 8 == 0 ? null : "1000");
		actions.add((action % 8) / 4 == 0 ? null : "0100");
		actions.add((action % 8 % 4) / 2 == 0 ? null : "0010");
		actions.add((action % 8 % 4 % 2) / 1 == 0 ? null : "0001");
		return actions;
	}

	public static int transToInt(Set<String> actions) {
		int action = 0;
		if (actions != null) {
			for (String temAction : actions) {
				if (temAction != null && !temAction.equals(""))
					action = action+Integer.parseInt(temAction, 2);
			}
		}
		return action;
	}
}
