package controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import views.SystemView;

public class SettingsController implements MouseListener{
    
    private SystemView views;
    
    public SettingsController(SystemView views){
       this.views = views;
       this.views.jLabelQuimica.addMouseListener(this);
       this.views.jLabelHematologia.addMouseListener(this);
       this.views.jLabelLiquidosCorporales.addMouseListener(this);
       this.views.jLabelMolecular.addMouseListener(this);
       this.views.jLabelMicrobiologia.addMouseListener(this);
       this.views.jLabelGenetica.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()== views.jLabelQuimica){
            views.jPanelQuimica.setBackground(new Color(107, 210, 214));
        }else if(e.getSource() == views.jLabelHematologia){
            views.jPanelHematologia.setBackground(new Color(107, 210, 214));
        }else if(e.getSource() == views.jLabelLiquidosCorporales){
            views.jPanelLiquidosCorporales.setBackground(new Color(107, 210, 214));
        }else if(e.getSource() == views.jLabelMolecular){
            views.jPanelMolecular.setBackground(new Color(107, 210, 214));
        }else if(e.getSource() == views.jLabelMicrobiologia){
            views.jPanelMicrobiologia.setBackground(new Color(107, 210, 214));
        }else if(e.getSource() == views.jLabelGenetica){
            views.jPanelGeneticas.setBackground(new Color(107, 210, 214));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()== views.jLabelQuimica){
            views.jPanelQuimica.setBackground(new Color(96,112,191));
        }else if(e.getSource() == views.jLabelHematologia){
            views.jPanelHematologia.setBackground(new Color(96,112,191));
        }else if(e.getSource() == views.jLabelLiquidosCorporales){
            views.jPanelLiquidosCorporales.setBackground(new Color(96,112,191));
        }else if(e.getSource() == views.jLabelMolecular){
            views.jPanelMolecular.setBackground(new Color(96,112,191));
        }else if(e.getSource() == views.jLabelMicrobiologia){
            views.jPanelMicrobiologia.setBackground(new Color(96,112,191));
        }else if(e.getSource() == views.jLabelGenetica){
            views.jPanelGeneticas.setBackground(new Color(96,112,191));
        }
    }
    
}
