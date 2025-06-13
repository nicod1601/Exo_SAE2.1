package MPM.metier;

import java.awt.Color;

public enum Couleur
{
	ROUGE          (255,   0,   0),
	VERT           (  0, 128,   0),
	BLEU           (  0,   0, 255),
	JAUNE          (255, 255,   0),

	MANGENTA       (255,   0, 255),
	BLEU_CLAIR     (  0, 255, 255),
	BORDEAUX       (128,    0,  0),
	VERT_FONCE     (  0, 128 ,  0),

	BLEU_MARINE    (  0,   0, 128),
	CORAIL_CLAIR   (128, 128,   0),
	VIOLET         (128,   0, 128),
	BLEU_SARCELLE  (  0, 128, 128),

	ROUGE_FONCE    (192,   0,   0),
	VERT_FEUILLE   (  0, 192 ,  0),
	CYAN           (  0,   0, 192),
	ORANGE         (255, 128,   0),

	ROSE_FONCE     (255,   0, 128),
	VERT_CITRON    (128, 255,   0),
	VERT_SARCELLE  (  0, 255, 128),
	GRIS           (128, 128, 128),

	GRIS_CLAIR     (192, 192, 192),
	BLEU_OCEAN     (  0, 64 , 128),
	VIOLET_ROYAL   (128,   0, 192),
	OCRE           (192, 128,   0),

	JAUNE_PALE     (255, 255, 128),
	TURQUOISE_FONCE(  0, 192, 128),
	VERT_MENTHE    (128, 192, 128),
	MAGENTA_CLAIR  (255, 128, 255),

	TURQUOISE_CLAIR(128, 255, 255),
	JAUNE_OLIVE    (192, 192, 128),
	MAUVE          (192, 128, 192),
	ROSE_SAUMON    (255, 128, 128);

	private int rouge, vert, bleu;

	Couleur (  int rouge, int vert, int bleu)
	{

	this.rouge = rouge;
	this.vert  = vert;
	this.bleu  = bleu;
	}

	public Color getCouleur() { return new Color(this.rouge, this.vert, this.bleu); }

	public int getRouge () { return this.rouge;}
	public int getVert  () { return this.vert; }
	public int getBleu  () { return this.bleu; }

}