package com.excelsior.xds.ui.editor.commons.reconciler;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;

/**
 * A reconciling strategy consisting of a sequence of internal reconciling strategies.
 * By default, all requests are passed on to the contained strategies.
 */
public class CompositeReconcilingStrategy implements IReconcilingStrategy
                                                   , IReconcilingStrategyExtension 
{
    /** The list of internal reconciling strategies. */
    private IReconcilingStrategy[] strategies;

    
    /**
     * Creates a new, empty composite reconciling strategy.
     */
    public CompositeReconcilingStrategy() {
    }

    /**
     * Sets the reconciling strategies for this composite strategy.
     *
     * @param strategies the strategies to be set or <code>null</code>
     */
    public void setReconcilingStrategies(IReconcilingStrategy[] strategies) {
        this.strategies = strategies;
    }

    /**
     * Returns the previously set strategies or <code>null</code>.
     *
     * @return the contained strategies or <code>null</code>
     */
    public IReconcilingStrategy[] getReconcilingStrategies() {
        return strategies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDocument(IDocument document) {
        if (strategies == null)
            return;

        for (IReconcilingStrategy strategy : strategies) {
            strategy.setDocument(document);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
        if (strategies == null)
            return;

        for (IReconcilingStrategy strategy : strategies) {
            strategy.reconcile(dirtyRegion, subRegion);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reconcile(IRegion partition) {
        if (strategies == null)
            return;

        for (IReconcilingStrategy strategy : strategies) {
            strategy.reconcile(partition);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProgressMonitor(IProgressMonitor monitor) {
        if (strategies == null)
            return;

        for (IReconcilingStrategy strategy : strategies) {
            if (strategy instanceof IReconcilingStrategyExtension) {
                IReconcilingStrategyExtension extension = (IReconcilingStrategyExtension)strategy;
                extension.setProgressMonitor(monitor);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialReconcile() {
        if (strategies == null)
            return;

        for (IReconcilingStrategy strategy : strategies) {
            if (strategy instanceof IReconcilingStrategyExtension) {
                IReconcilingStrategyExtension extension = (IReconcilingStrategyExtension)strategy;
                extension.initialReconcile();
            }
        }
    }
    
}
