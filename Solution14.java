import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;
/*
 * @Description
 * 复原 IP 地址
 * 有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。
 * 例如："0.1.2.201" 和 "192.168.1.1" 是有效IP地址，但是 "0.011.255.245"、"192.168.1.312" 和 "192.168@1.1" 是 无效 IP 地址。
 * 给定一个只包含数字的字符串 s ，用以表示一个 IP 地址，返回所有可能的有效 IP 地址，这些地址可以通过在 s 中插入 '.' 来形成。你不能重新排序或删除 s 中的任何数字。你可以按任何顺序返回答案。
 *
 * 示例 1：
 * 输入：s = "25525511135"
 * 输出：["255.255.11.135","255.255.111.35"]
 * 示例 2：
 *  输入：s = "0000"
 * 输出：["0.0.0.0"]
 * 示例 3：
 * 输入：s = "101023"
 * 输出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
 *
 */
class Solution {
    static final int SEG_COUNT = 4;
    List<String> ans = new ArrayList<String>();
    int[] segments  = new int[SEG_COUNT];

    public List<String> restoreIpAddresses(String s) {
        segments = new int[SEG_COUNT];
        dfs(s, 0, 0);
        return ans;
    }

    public void dfs(String s, int segId, int segStart) {
        // 如果找到了 4 段 IP 地址并且遍历完了字符串，那么就是一种答案
        if (segId == SEG_COUNT) {
            if (segStart == s.length()) {
                StringBuffer ipAddr = new StringBuffer();
                for (int i = 0; i < SEG_COUNT; ++i) {
                    ipAddr.append(segments[i]);
                    if (i != SEG_COUNT - 1) {
                        ipAddr.append('.');
                    }
                }
                ans.add(ipAddr.toString());
            }
            return;
        }

        // 如果还没有找到 4 段 IP 地址就已经遍历完了字符串，那么提前回溯
        if (segStart == s.length()) {
            return;
        }

        // 由于不能有前导零，如果当前数字为 0，那么这一段 IP 地址只能为 0
        if (s.charAt(segStart) == '0') {
            segments[segId] = 0;
            dfs(s, segId + 1, segStart + 1);
            return;
        }

        // 一般情况，枚举每一种可能性并递归
        int addr = 0;
        for (int segEnd = segStart; segEnd < s.length(); ++segEnd) {
            addr = addr * 10 + (s.charAt(segEnd) - '0');
            if (addr > 0 && addr <= 0xFF) {
                segments[segId] = addr;
                dfs(s, segId + 1, segEnd + 1);
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        List<String> ans = sol.restoreIpAddresses("1234");
        System.out.println(ans);
    }
}
public class L2022211988_14_Test {
    /*
        我们在测试中采用等价类划分原则，可能的情况有以下几种：
        1.字符串s只有四位数字，全零或非全零
        2.字符串s划分后，有两种情况
        3.字符串s划分后，有多种情况
        4.字符串s无法形成正确的ip地址
     */
    //测试用例‘1234’，测试四位非全零字符串
    @Test
    public void test_1(){
        Solution sol = new Solution();
        List<String> ans = sol.restoreIpAddresses("1234");
        List<String> ans2 = new ArrayList<>();
        ans2.add("1.2.3.4");
        assertEquals(ans2 ,ans);
    }
    //测试用例‘0000’，测试四位全零字符串
    @Test
    public void test_2(){
        Solution sol = new Solution();
        List<String> ans = sol.restoreIpAddresses("0000");
        List<String> ans2 = new ArrayList<>();
        ans2.add("0.0.0.0");
        assertEquals(ans2 ,ans);
    }
    //测试用例‘25525511135’，测试划分情况有两种的字符串
    @Test
    public void test_3(){
        Solution sol = new Solution();
        List<String> ans = sol.restoreIpAddresses("25525511135");
        List<String> ans2 = new ArrayList<>();
        ans2.add("255.255.11.135");
        ans2.add("255.255.111.35");
        assertEquals(ans2 ,ans);
    }
    //测试用例‘101023’，测试划分情况有多种的字符串
    @Test
    public void test_4(){
        Solution sol = new Solution();
        List<String> ans = sol.restoreIpAddresses("101023");
        List<String> ans2 = new ArrayList<>();
        //["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
        ans2.add("1.0.10.23");
        ans2.add("1.0.102.3");
        ans2.add("10.1.0.23");
        ans2.add("10.10.2.3");
        ans2.add("101.0.2.3");
        assertEquals(ans2 ,ans);
    }
    //测试用例‘00000’，测试无法形成正确ip地址的字符串
    @Test
    public void test_5(){
        Solution sol = new Solution();
        List<String> ans = sol.restoreIpAddresses("00000");
        assertEquals(ans,new ArrayList<String>());
    }
}