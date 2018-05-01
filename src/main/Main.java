package main;


import java.util.*;

public class Main {

    private static final int CONTINUATION = 0;
    private static boolean lastOne = false;


    /**
     * bingoゲームを進行する
     * @param args
     */

    public static void main(String[] args) {
        List<List<String>> list = createNumbers();
        List<Integer> num = getRandomNumbers();

        //出力
        firstAnnouncement(list);
        list.get(2).set(2, "  ");
        Scanner scan = new Scanner(System.in);


        int resultCount = 0;
        int bingoFlg = 0;
        String winningList = "";
        boolean fin = true;
        while (fin) {
            String input = scan.nextLine();
            if (input != null) {

                // 現在のターン数、当選番号、当選結果を出力
                winningList = winningList.concat(String.valueOf(num.get(resultCount)) + ",");
                StringBuffer winning = new StringBuffer(winningList);
                int lastIndex = winning.lastIndexOf(",");

                System.out.println(resultCount + 1 + "ターン目");
                System.out.println("現在の当選結果は " + num.get(resultCount) + " 番です");
                System.out.println("今までの当選結果：" + winning.deleteCharAt(lastIndex));
                System.out.println();

                // 途中結果を出力
                announcement(list, num.get(resultCount));

                // 判定結果で終了か継続か
                bingoFlg = judgment(list);

                System.out.println();

                //ビンゴなら終了
                //リーチなら出力
                if(bingoFlg == 2){
                    System.out.println("ビンゴ！！");
                    fin = false;
                }
                if(lastOne && !(bingoFlg == 2)){
                    System.out.println("リーチ！！");
                }
                resultCount++;
            }
        }
        scan.close();
        System.out.println("終わり");
    }

    /**
     * シートの初期状態を出力
     * @param list
     */
    private static void firstAnnouncement(List<List<String>> list) {

        list.get(2).set(2, "W");
        for (List<String> res : list) {
            System.out.print("|");
            for (String str : res) {
                if (str.length() < 2) {
                    str += " ";
                }
                System.out.print(str);
                System.out.print("|");
            }
            System.out.println();
        }
    }

    /**
     * 途中経過を出力
     * @param list
     * @param number
     */
    private static void announcement(List<List<String>> list, int number) {

        int columnCount =0;
        for (List<String> res : list) {
            System.out.print("|");
            for (String str : res) {
                if (str.length() < 2) {
                    str += " ";
                }
                if (str.equals(String.valueOf(number))) {
                    str = "  ";
                    res.set(columnCount,"  ");
                }
                System.out.print(str);
                System.out.print("|");
                columnCount++;
            }
            columnCount = 0;
            System.out.println();
        }
    }

    /**
     * bingoか継続もしくはリーチかを判定する
     * @param list
     * @return
     */
    private static int judgment(List<List<String>> list) {

        //横判定
        int sideCount = 0;
        for (List<String> res : list) {
            for(String column: res){
                if(column.equals("  ")){
                    sideCount++;
                }
                if(sideCount == 5) {
                    return 2;
                }
            }
            if(sideCount == 4){
                lastOne = true;
            }
            sideCount = 0;
        }
        //縦判定
        int heightCount = 0;
        for (int length = 0; length < list.size(); length++) {
            for (List<String> res : list) {
                if(res.get(length).equals("  ")){
                    heightCount++;
                }
                if(heightCount == 5) {
                    return 2;
                }
            }
            if(heightCount == 4){
                lastOne = true;
            }
            heightCount = 0;
        }
        return CONTINUATION;
    }

    /**
     * ランダムな数字のリストを生成(5×5)
     * @return
     */
    private static List<List<String>> createNumbers() {

        List<List<String>> sheet = new ArrayList<>();
        List<Integer> numbers = getNumbers();
        List<String> rows1 = new ArrayList<>();
        List<String> rows2 = new ArrayList<>();
        List<String> rows3 = new ArrayList<>();
        List<String> rows4 = new ArrayList<>();
        List<String> rows5 = new ArrayList<>();
        int count = 0;
        for (int i : numbers) {
            count++;
            if (count <= 5) {
                rows1.add(String.valueOf(i));
            } else if (count <= 10) {
                rows2.add(String.valueOf(i));
            } else if (count <= 15) {
                rows3.add(String.valueOf(i));
            } else if (count <= 20) {
                rows4.add(String.valueOf(i));
            } else {
                rows5.add(String.valueOf(i));
            }
        }
        sheet.add(rows1);
        sheet.add(rows2);
        sheet.add(rows3);
        sheet.add(rows4);
        sheet.add(rows5);

        return sheet;
    }


    /**
     * 1〜99までのランダムな数字を生成
     * @return
     */
    private static List<Integer> getRandomNumbers() {

        List<Integer> numbers = new ArrayList<>();

        for (int i = 1; i < 100; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);


        return numbers;
    }

    /**
     * シートのサイズ分のランダムな番号を抜粋
     * @return
     */
    public static List<Integer> getNumbers() {
        List<Integer> result = new ArrayList<>();

        int count = 0;
        for (int i : getRandomNumbers()) {
            result.add(i);
            count++;
            if (count == 25) {
                break;
            }
        }
        return result;
    }
}
