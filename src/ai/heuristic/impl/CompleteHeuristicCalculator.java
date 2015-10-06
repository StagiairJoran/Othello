package ai.heuristic.impl;

import ai.heuristic.api.HeuristicCalculator;
import model.Bord;
import model.Kleur;

/**
 * Created by jorandeboever
 * on 6/10/15.
 * Source: https://kartikkukreja.wordpress.com/2013/03/30/heuristic-function-for-reversiothello/
 */
public class CompleteHeuristicCalculator implements HeuristicCalculator {
    public double getHeuristicValue(Bord bord, Kleur kleur) {
        double coinParityHeuristicValue = getCoinParityHeuristicValue(bord, kleur);
        double mobilityHeuristicValue = getMobilityHeuristicValue(bord, kleur);
        double cornerHeuristicValue = getCornerOccupancyHeuristicValue(bord, kleur);
        double stabilityHeuristicValue = getStabilityHeuristicValue(bord, kleur);
        double cornerClosenessHeuristicValue = getCornerClosenessHeuristicValue(bord, kleur);

        System.out.println("Coin Heuristic Value = " + coinParityHeuristicValue);
        return (10 * coinParityHeuristicValue) + (78.922 * mobilityHeuristicValue) + (801.724 * cornerHeuristicValue) + stabilityHeuristicValue + (382.026 * cornerClosenessHeuristicValue);
    }


    private double getStabilityHeuristicValue(Bord bord, Kleur kleur) {
        int stabilityHeuristicValue = 0;
        int aantalMax = 0;
        int aantalMin = 0;
        Kleur andereKleur = Kleur.andereKleur(kleur);

        int X1[] = {-1, -1, 0, 1, 1, 1, 0, -1};
        int Y1[] = {0, 1, 1, 1, 0, -1, -1, -1};

        int[][] opzoekTabel = new int[8][8];

        opzoekTabel[0] = new int[]{20, -3, 11, 8, 8, 11, -3, 20};
        opzoekTabel[1] = new int[]{-3, -7, -4, 1, 1, -4, -7, -3};
        opzoekTabel[2] = new int[]{11, -4, 2, 2, 2, 2, -4, 11};
        opzoekTabel[3] = new int[]{8, 1, 2, -3, -3, 2, 1, 8};
        opzoekTabel[4] = new int[]{8, 1, 2, -3, -3, 2, 1, 8};
        opzoekTabel[5] = new int[]{11, -4, 2, 2, 2, 2, -4, 11};
        opzoekTabel[6] = new int[]{-3, -7, -4, 1, 1, -4, -7, -3};
        opzoekTabel[7] = new int[]{20, -3, 11, 8, 8, 11, -3, 20};

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (bord.getKleurOpPositie(i, j) == kleur) {
                    stabilityHeuristicValue += opzoekTabel[i][j];
                } else if (bord.getKleurOpPositie(i, j) == andereKleur) {
                    stabilityHeuristicValue -= opzoekTabel[i][j];
                }

                if (bord.getKleurOpPositie(i, j) == Kleur.LEEG) {
                    for (int k = 0; k < 8; k++) {
                        int rij = i + X1[k];
                        int kolom = j + Y1[k];
                        if (rij >= 0 && rij < 8 && kolom >= 0 && kolom < 8 && bord.getKleurOpPositie(rij, kolom) == Kleur.LEEG) {
                            if (bord.getKleurOpPositie(rij, kolom) == kleur) aantalMax++;
                            else aantalMin++;
                            break;
                        }
                    }
                }
            }
        }
        double frontTileHeuristicValue;
        if (aantalMax > aantalMin) {
            frontTileHeuristicValue = -(100.0 * aantalMax) / (aantalMax + aantalMin);
        } else if (aantalMax < aantalMin)
            frontTileHeuristicValue = (100.0 * aantalMin) / (aantalMax + aantalMin);
        else frontTileHeuristicValue = 0;


        return (stabilityHeuristicValue * 10) + (74.396 * frontTileHeuristicValue);
    }

    private double getCornerOccupancyHeuristicValue(Bord bord, Kleur kleur) {
        int aantalMax = 0;
        int aantalMin = 0;

        if (bord.getKleurOpPositie(0, 0) == kleur) {
            aantalMax++;
        }
        if (bord.getKleurOpPositie(0, bord.getGrootteBord() - 1) == kleur) {
            aantalMax++;
        }
        if (bord.getKleurOpPositie(bord.getGrootteBord() - 1, 0) == kleur) {
            aantalMax++;
        }
        if (bord.getKleurOpPositie(bord.getGrootteBord() - 1, bord.getGrootteBord() - 1) == kleur) {
            aantalMax++;
        }
        if (bord.getKleurOpPositie(0, 0) == Kleur.andereKleur(kleur)) {
            aantalMin++;
        }
        if (bord.getKleurOpPositie(0, bord.getGrootteBord() - 1) == Kleur.andereKleur(kleur)) {
            aantalMin++;
        }
        if (bord.getKleurOpPositie(bord.getGrootteBord() - 1, 0) == Kleur.andereKleur(kleur)) {
            aantalMin++;
        }
        if (bord.getKleurOpPositie(bord.getGrootteBord() - 1, bord.getGrootteBord() - 1) == Kleur.andereKleur(kleur)) {
            aantalMin++;
        }


        return 25 * (aantalMax - aantalMin);
    }

    private double getCornerClosenessHeuristicValue(Bord bord, Kleur kleur) {
        int aantalMax = 0;
        int aantalMin = 0;

        int grootteBord = bord.getGrootteBord();
        Kleur andereKleur = Kleur.andereKleur(kleur);

        if (bord.getKleurOpPositie(0, 0) == Kleur.LEEG) {
            if (bord.getKleurOpPositie(0, 1) == kleur) {
                aantalMax++;
            }
            if (bord.getKleurOpPositie(0, 1) == andereKleur) {
                aantalMin++;
            }
            if (bord.getKleurOpPositie(1, 1) == kleur) {
                aantalMax++;
            }
            if (bord.getKleurOpPositie(1, 1) == andereKleur) {
                aantalMin++;
            }
            if (bord.getKleurOpPositie(1, 0) == kleur) {
                aantalMax++;
            }
            if (bord.getKleurOpPositie(1, 0) == andereKleur) {
                aantalMin++;
            }
        }
        if (bord.getKleurOpPositie(0, grootteBord - 1) == Kleur.LEEG) {
            if (bord.getKleurOpPositie(0, grootteBord - 1) == kleur) {
                aantalMax++;
            }
            if (bord.getKleurOpPositie(0, grootteBord - 1) == andereKleur) {
                aantalMin++;
            }
            if (bord.getKleurOpPositie(1, grootteBord - 1) == kleur) {
                aantalMax++;
            }
            if (bord.getKleurOpPositie(1, grootteBord - 1) == andereKleur) {
                aantalMin++;
            }
            if (bord.getKleurOpPositie(1, grootteBord - 2) == kleur) {
                aantalMax++;
            }
            if (bord.getKleurOpPositie(1, grootteBord - 2) == andereKleur) {
                aantalMin++;
            }
        }
        if (bord.getKleurOpPositie(grootteBord - 1, 0) == Kleur.LEEG) {
            if (bord.getKleurOpPositie(grootteBord - 1, 1) == kleur) {
                aantalMax++;
            }
            if (bord.getKleurOpPositie(grootteBord - 1, 1) == andereKleur) {
                aantalMin++;
            }
            if (bord.getKleurOpPositie(grootteBord - 2, 1) == kleur) {
                aantalMax++;
            }
            if (bord.getKleurOpPositie(grootteBord - 2, 1) == andereKleur) {
                aantalMin++;
            }
            if (bord.getKleurOpPositie(grootteBord - 1, 0) == kleur) {
                aantalMax++;
            }
            if (bord.getKleurOpPositie(grootteBord - 1, 0) == andereKleur) {
                aantalMin++;
            }
        }
        if (bord.getKleurOpPositie(0, 0) == Kleur.LEEG) {
            if (bord.getKleurOpPositie(grootteBord - 2, grootteBord - 1) == kleur) {
                aantalMax++;
            }
            if (bord.getKleurOpPositie(grootteBord - 2, grootteBord - 1) == andereKleur) {
                aantalMin++;
            }
            if (bord.getKleurOpPositie(grootteBord - 1, grootteBord - 2) == kleur) {
                aantalMax++;
            }
            if (bord.getKleurOpPositie(grootteBord - 1, grootteBord - 2) == andereKleur) {
                aantalMin++;
            }
            if (bord.getKleurOpPositie(grootteBord - 2, grootteBord - 2) == kleur) {
                aantalMax++;
            }
            if (bord.getKleurOpPositie(grootteBord - 2, grootteBord - 2) == andereKleur) {
                aantalMin++;
            }
        }

        return -12.5 * (aantalMax - aantalMin);
    }

    private double getCoinParityHeuristicValue(Bord bord, Kleur kleur) {
        int aantalMax = 0;
        int aantalMin = 0;

        for (int i = 0; i < bord.getGrootteBord() - 1; i++) {
            for (int j = 0; j < bord.getGrootteBord() - 1; j++) {
                if (bord.getKleurOpPositie(i, j) == kleur) {
                    aantalMax++;
                } else if (bord.getKleurOpPositie(i, j) != Kleur.LEEG) {
                    aantalMin++;
                }
            }
        }

        if (aantalMax > aantalMin)
            return (100.0 * aantalMax) / (aantalMax + aantalMin);
        else if (aantalMax < aantalMin)
            return -(100.0 * aantalMin) / (aantalMax + aantalMin);
        else return 0;


    }

    private double getMobilityHeuristicValue(Bord bord, Kleur kleur) {
        int aantalMovesMax = 0;
        int aantalMovesMin = 0;
        for (int i = 0; i < bord.getGrootteBord() - 1; i++) {
            for (int j = 0; j < bord.getGrootteBord() - 1; j++) {
                if (bord.isGeldigeZet(i, j, kleur)) {
                    aantalMovesMax++;
                }
                if (bord.isGeldigeZet(i, j, Kleur.andereKleur(kleur))) {
                    aantalMovesMin++;
                }
            }
        }

        if (aantalMovesMax > aantalMovesMin)
            return (100.0 * aantalMovesMax) / (aantalMovesMax + aantalMovesMin);
        else if (aantalMovesMax < aantalMovesMin)
            return -(100.0 * aantalMovesMin) / (aantalMovesMax + aantalMovesMin);
        else return 0;
    }

}
