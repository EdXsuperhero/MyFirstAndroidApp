package csc207project.gamecentre.TwentyFourGame;
/*
Adapted from:
https://leetcode.com/problems/24-game/solution/
*/
import java.util.ArrayList;

/**
 * The class is to check if the 4 randomly generated number is solvable or not.
 */
public class checkSolvable {
    /**
     * This method is to check if the input integer list of 4 number is solvable or not.
     * @param nums the input list of integer num
     * @return return turn the the list of number is solvable, otherwise return false
     */
    public boolean judgePoint24(int[] nums) {
        ArrayList A = new ArrayList<Double>();
        for (int v: nums) A.add((double) v);
        return solve(A);
    }

    /**
     * Check the input arrayList of double is solvable or not.
     * @param nums arrayList of double nums
     * @return return true if solvale, otherwise return false
     */
     private boolean solve(ArrayList<Double> nums) {
        if (nums.size() == 0) return false;
        if (nums.size() == 1) return Math.abs(nums.get(0) - 24) < 1e-6;

        for (int i = 0; i < nums.size(); i++) {
            for (int j = 0; j < nums.size(); j++) {
                if (i != j) {
                    ArrayList<Double> nums2 = new ArrayList<Double>();
                    for (int k = 0; k < nums.size(); k++) if (k != i && k != j) {
                        nums2.add(nums.get(k));
                    }
                    for (int k = 0; k < 4; k++) {
                        if (k < 2 && j > i) continue;
                        if (k == 0) nums2.add(nums.get(i) + nums.get(j));
                        if (k == 1) nums2.add(nums.get(i) * nums.get(j));
                        if (k == 2) nums2.add(nums.get(i) - nums.get(j));
                        if (k == 3) {
                            if (nums.get(j) != 0) {
                                nums2.add(nums.get(i) / nums.get(j));
                            } else {
                                continue;
                            }
                        }
                        if (solve(nums2)) return true;
                        nums2.remove(nums2.size() - 1);
                    }
                }
            }
        }
        return false;
    }
}
